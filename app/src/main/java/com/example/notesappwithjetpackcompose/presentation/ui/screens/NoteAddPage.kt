package com.example.notesappwithjetpackcompose.presentation.ui.screens

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import com.example.notesappwithjetpackcompose.R
import com.example.notesappwithjetpackcompose.presentation.ui.components.NoteTopBar
import com.example.notesappwithjetpackcompose.presentation.ui.theme.md_theme_light_tertiaryContainer
import com.example.notesappwithjetpackcompose.presentation.viewmodel.NoteAddPageViewModel
import dagger.hilt.android.ViewModelLifecycle
import kotlinx.coroutines.launch
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteAddPage(navController: NavController, noteId: Int) {
    val viewmodel: NoteAddPageViewModel = hiltViewModel()
    val tfNoteTitle = remember { mutableStateOf("") }
    val tfNoteDetail = remember { mutableStateOf("") }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val isTitleEmpty = remember { mutableStateOf(false) }

    val note = viewmodel.note.observeAsState()
    LaunchedEffect(key1 = true){
        if (noteId != -1){
            viewmodel.getNoteById(noteId)



            if (note.value != null){
                println(note.value!!.note_title)
                tfNoteTitle.value = note.value!!.note_title
                tfNoteDetail.value = note.value!!.note_detail
            }else{
                println("note boş")
            }
        }
    }


    Scaffold(
        topBar = {
            NoteTopBar(
                title = if (noteId == -1) stringResource(id = R.string.addNote) else stringResource(id = R.string.editNote),
                isSearching = false,
                isCanBack = true,
                searchingHandler = {},
                actionMainHandler = {},
                actionSecondHandler = {
                    scope.launch {
                        if (tfNoteTitle.value.isNotEmpty()) {

                            if (noteId == -1){
                                viewmodel.saveNote(
                                    tfNoteTitle.value,
                                    tfNoteDetail.value,
                                    LocalDate.now().toString()
                                )
                            }else{
                                viewmodel.updateNote(noteId,tfNoteTitle.value,tfNoteDetail.value,LocalDate.now().toString())
                            }
                            navController.navigate("main_page")
                        } else {
                            isTitleEmpty.value = true
                        }
                    }
                },
                mainActionIcon = R.drawable.done_icon,
                secondActionIcon = null,
                leftIcon = R.drawable.back_icon,
                leftActionHandler = {
                    navController.navigate("main_page")
                },
                menuAvailable = false,
                menuItemHandler = {}
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(md_theme_light_tertiaryContainer)
                    .padding(top = it.calculateTopPadding(), start = 20.dp, end = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,

                ) {
                TextField(
                    value = tfNoteTitle.value,
                    onValueChange = {
                        isTitleEmpty.value = false
                        if (it.length <= 15) {
                            tfNoteTitle.value = it
                        }
                    },
                    label = {
                        Text(
                            text = stringResource(id = R.string.noteTitle),
                            fontWeight = FontWeight.Bold
                        )
                    },
                    singleLine = true,
                    maxLines = 1,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.White,

                        ),
                    isError = isTitleEmpty.value
                )
                TextField(
                    value = tfNoteDetail.value,
                    onValueChange = { tfNoteDetail.value = it },
                    label = { Text(text = stringResource(id = R.string.noteDetail)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                )

            }
        }
    )
}

