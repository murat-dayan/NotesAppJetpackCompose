package com.example.notesappwithjetpackcompose.presentation.scenes.add_note

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.notesappwithjetpackcompose.R
import com.example.notesappwithjetpackcompose.core.common.Priority
import com.example.notesappwithjetpackcompose.domain.model.Note
import com.example.notesappwithjetpackcompose.presentation.components.NoteTopBar
import com.example.notesappwithjetpackcompose.presentation.components.TextFieldItem
import com.example.notesappwithjetpackcompose.presentation.navigation.Screen
import com.example.notesappwithjetpackcompose.presentation.state.CRUDState
import com.example.notesappwithjetpackcompose.presentation.ui.theme.md_theme_light_tertiaryContainer
import kotlinx.coroutines.launch
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "ResourceType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteAddPage(
    navController: NavController,
    noteId: Int,
    noteAddPageViewModel: NoteAddPageViewModel
) {
    var tfNoteTitle by remember { mutableStateOf("") }
    var tfNoteDetail by remember { mutableStateOf("") }
    var selectedPriority by rememberSaveable { mutableStateOf(Priority.LOW) } // Initial priority
    var priorityMenuvisible by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val isTitleEmpty = remember { mutableStateOf(false) }

    val noteState = noteAddPageViewModel.noteState.collectAsStateWithLifecycle().value

    LaunchedEffect(noteId) {
        if (noteId != -1) {
            noteAddPageViewModel.getNoteById(noteId)

        }
    }

    if (noteState.note != null) {
        tfNoteTitle = noteState.note.note_title
        tfNoteDetail = noteState.note.note_detail
        selectedPriority = Priority.valueOf(noteState.note.priority.toString())
    }


    Scaffold(
        topBar = {
            NoteTopBar(
                title = if (noteId == -1) stringResource(id = R.string.addNote) else stringResource(
                    id = R.string.editNote
                ),
                isSearching = false,
                isCanBack = true,
                searchingHandler = {},
                actionMainHandler = {},
                actionSecondHandler = {
                    scope.launch {
                        if (tfNoteTitle.isNotEmpty()) {

                            if (noteId == -1) {
                                val newNote = Note(
                                    note_id = null,
                                    tfNoteTitle,
                                    tfNoteDetail,
                                    LocalDate.now().toString()
                                )
                                noteAddPageViewModel.addNote(newNote)
                            } else {
                                val updatedNote = Note(
                                    noteId,
                                    tfNoteTitle,
                                    tfNoteDetail,
                                    LocalDate.now().toString()
                                )
                                noteAddPageViewModel.updateNote(updatedNote)
                            }
                            navController.navigate(Screen.MainScreen.route)
                        } else {
                            isTitleEmpty.value = true
                        }
                    }
                },
                mainActionIcon = R.drawable.done_icon,
                secondActionIcon = null,
                leftIcon = R.drawable.back_icon,
                leftActionHandler = {
                    navController.navigate(
                        Screen.MainScreen.route
                    )
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
                TextFieldItem(
                    value = tfNoteTitle,
                    onValueChange = {
                        isTitleEmpty.value = false
                        if (it.length <= 15) {
                            tfNoteTitle = it
                        }
                    },
                    labelId = R.string.noteTitle,
                    maxlines = 1,
                )
                TextFieldItem(
                    value = tfNoteDetail,
                    onValueChange = { tfNoteDetail = it },
                    labelId = R.string.noteDetail,
                    maxlines = 1,
                )
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextFieldItem(
                        value = selectedPriority.toString(),
                        onValueChange = {
                            val newText = selectedPriority.toString()
                        },
                        labelId = R.string.note_priority,
                        maxlines = 1,
                        trailingIcon = {
                            IconButton(onClick = {
                                priorityMenuvisible = true
                            }) {
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowDown,
                                    contentDescription = null,
                                    tint = Color.White
                                )
                            }
                        },
                        enabled = false
                    )
                    DropdownMenu(
                        expanded = priorityMenuvisible,
                        onDismissRequest = { priorityMenuvisible = false }) {

                        DropdownMenuItem(
                            text = { Text(text = Priority.LOW.toString()) },
                            onClick = {
                                selectedPriority = Priority.LOW
                                priorityMenuvisible = false
                            })
                        DropdownMenuItem(
                            text = { Text(text = Priority.MEDIUM.toString()) },
                            onClick = {
                                selectedPriority = Priority.MEDIUM
                                println(selectedPriority.toString())
                                priorityMenuvisible = false
                            })
                        DropdownMenuItem(
                            text = { Text(text = Priority.HIGH.toString()) },
                            onClick = {
                                selectedPriority = Priority.HIGH
                                priorityMenuvisible = false
                            })

                    }
                }


            }
        }
    )
}



