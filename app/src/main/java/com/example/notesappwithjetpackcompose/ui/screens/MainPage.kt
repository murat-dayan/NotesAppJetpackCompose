package com.example.notesappwithjetpackcompose.ui.screens

import android.annotation.SuppressLint
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.notesappwithjetpackcompose.R
import com.example.notesappwithjetpackcompose.ui.theme.md_theme_light_primary
import com.example.notesappwithjetpackcompose.ui.theme.md_theme_light_tertiaryContainer
import com.example.notesappwithjetpackcompose.viewmodel.MainPageViewModel
import com.google.gson.Gson
import kotlinx.coroutines.launch
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun MainPage(navController: NavController) {

    val context = LocalContext.current
    val viewmodel : MainPageViewModel = hiltViewModel()
    val notesList = viewmodel.notesList.observeAsState(listOf())
    val isSearching = remember { mutableStateOf(false) }
    val tfSearch = remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true){
        viewmodel.loadNotes()
    }

    Scaffold (
        topBar = {
            TopAppBar(
                title = {

                    if (isSearching.value){
                        TextField(
                            value = tfSearch.value ,
                            onValueChange = {
                                tfSearch.value = it
                                viewmodel.searchNotes(it)
                            },
                            placeholder = {
                                Text(
                                    text = "Search...",
                                    color = Color.White,
                                    textAlign = TextAlign.Center
                                )
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = md_theme_light_primary,
                                textColor = Color.White
                            ),
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Search
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }else{
                        Text(
                            text = "Notes",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    }

                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = md_theme_light_primary,
                    titleContentColor = Color.White
                ),

                actions = {
                    if (isSearching.value){
                        IconButton(
                            onClick = {
                                isSearching.value = false
                                tfSearch.value=""
                            }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.close__icon),
                                contentDescription = "search",
                                tint = Color.White
                            )
                        }
                    }else{
                        IconButton(
                            onClick = {
                                isSearching.value = true
                                tfSearch.value=""
                            }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.search_icon),
                                contentDescription = "close",
                                tint = Color.White
                            )
                        }
                    }
                }
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
                    .background(md_theme_light_tertiaryContainer)
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
                                    containerColor = md_theme_light_primary
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
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = note.note_date!!,
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
                    )
                },
            )
        }
    )
}