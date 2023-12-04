package com.example.notesappwithjetpackcompose

import android.annotation.SuppressLint
import android.app.Application
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.notesappwithjetpackcompose.entity.Note
import com.example.notesappwithjetpackcompose.ui.theme.NotesAppWithJetpackComposeTheme
import com.example.notesappwithjetpackcompose.viewmodel.MainPageViewModel
import com.example.notesappwithjetpackcompose.viewmodelfactory.MainPageViewModelFactory
import com.google.gson.Gson
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotesAppWithJetpackComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHosting()
                }
            }
        }
    }
}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavHosting(){
    val navCont = rememberNavController()

    NavHost(
        navController = navCont,
        startDestination = "main_page"){

        composable("main_page"){
            MainPage(navController = navCont)
        }
        composable("note_add_page"){
            NoteAddPage(navCont)
        }
        composable(
            "note_detail_page/{note}",
            arguments = listOf(
                navArgument("note"){type = NavType.StringType}
            )
        ){
            val json = it.arguments?.getString("note")
            val note = Gson().fromJson(json,Note::class.java)
            NoteDetailPage(note,navCont)
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun MainPage(navController: NavController) {

    val context = LocalContext.current
    val viewmodel : MainPageViewModel = viewModel(
        factory = MainPageViewModelFactory(context.applicationContext as Application)
    )
    val notesList = viewmodel.notesList.observeAsState(listOf())

    val snackbarHostState = remember { SnackbarHostState()}
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true){
        viewmodel.loadNotes()
    }

    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Notes",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color.Black,
                    titleContentColor = colorResource(id = R.color.gold)
                )
            )
        },
        snackbarHost = {
                       SnackbarHost(
                           hostState = snackbarHostState
                       )
        },
        content = {innerPading->


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(id = R.color.gold)),
                
            ) {
                LazyColumn(
                    modifier = Modifier.consumeWindowInsets(innerPading),
                    contentPadding = innerPading,

                    ){
                    items(
                        count = notesList.value.count(),
                        itemContent = {
                            val note = notesList.value[it]

                            Card (
                                modifier = Modifier.padding(3.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color.Black
                                )
                            ){
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp)
                                        .clickable {
                                            val noteJson = Gson().toJson(note)
                                            navController.navigate("note_detail_page/$noteJson")
                                        }
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = note.note_title,
                                            fontSize = 18.sp,
                                            color = colorResource(id = R.color.gold),
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = note.note_date!!,
                                            color = Color.White,
                                            fontStyle = FontStyle.Italic,
                                            fontSize = 14.sp
                                        )
                                        IconButton(
                                            onClick = {
                                                scope.launch {
                                                    val sb = snackbarHostState.showSnackbar(
                                                        message = "Do you want to delete?",
                                                        actionLabel = "Yes",
                                                        duration = SnackbarDuration.Short
                                                    )
                                                    if (sb == SnackbarResult.ActionPerformed){
                                                        viewmodel.deleteNote(note.note_id)
                                                    }
                                                }
                                            },
                                            modifier = Modifier.size(24.dp)
                                        ) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.delete_icon),
                                                contentDescription = null,
                                                tint = Color.White
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    )
                }
            }

        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                          navController.navigate("note_add_page")
                },
                content = {
                    Icon(
                        painter = painterResource(id = R.drawable.add_icon),
                        contentDescription = null,
                        tint = colorResource(id = R.color.gold)
                    )
                },
                containerColor = Color.Black
            )
        }
    )
}

