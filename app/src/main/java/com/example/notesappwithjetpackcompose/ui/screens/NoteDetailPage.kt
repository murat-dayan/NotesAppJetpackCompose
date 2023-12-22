package com.example.notesappwithjetpackcompose.ui.screens

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.notesappwithjetpackcompose.entity.Note
import com.example.notesappwithjetpackcompose.ui.theme.md_theme_light_primary
import com.example.notesappwithjetpackcompose.ui.theme.md_theme_light_tertiaryContainer
import com.example.notesappwithjetpackcompose.viewmodel.NoteDetailViewModel
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailPage(note: Note, navController: NavController) {
    val context = LocalContext.current
    val viewmodel: NoteDetailViewModel = hiltViewModel()
    val tfNoteTitle = remember { mutableStateOf("") }
    val tfNoteDetail = remember { mutableStateOf("") }
    val isValuesChanged = remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        tfNoteTitle.value = note.note_title
        tfNoteDetail.value = note.note_detail
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Note Detail",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = md_theme_light_primary,
                    titleContentColor = Color.White
                )
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(md_theme_light_tertiaryContainer),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                TextField(
                    value = tfNoteTitle.value,
                    onValueChange ={
                        if (it.length <=15){
                            tfNoteTitle.value = it
                            isValuesChanged.value = true
                        }
                    },
                    label = { Text(text = "Your Note title") },
                    singleLine = true,
                    modifier = Modifier.width(300.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.White,
                        focusedIndicatorColor = Color.Black
                    ),

                )
                TextField(
                    value = tfNoteDetail.value,
                    onValueChange ={
                        tfNoteDetail.value = it
                        isValuesChanged.value = true
                    },
                    label = { Text(text = "Your Note Detail")},
                    modifier = Modifier.width(300.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.White,
                        focusedIndicatorColor = Color.Black
                    )
                )
                Button(
                    onClick = {
                        if (isValuesChanged.value && tfNoteTitle.value.isNotEmpty()){
                            viewmodel.updateNote(
                                note.note_id,
                                tfNoteTitle.value,
                                tfNoteDetail.value,
                                "${LocalDate.now()}(edited)"
                            )
                            navController.navigate("main_page")
                        }else{
                            navController.navigate("main_page")
                        }
                    },
                    modifier = Modifier.size(300.dp, 40.dp),

                ) {
                    Text(
                        text = "Update",
                        fontSize = 19.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxSize(),
                    )
                }

            }
        }
    )
}
