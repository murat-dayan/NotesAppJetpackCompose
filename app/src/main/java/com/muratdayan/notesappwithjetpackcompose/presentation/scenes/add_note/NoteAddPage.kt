package com.muratdayan.notesappwithjetpackcompose.presentation.scenes.add_note

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.muratdayan.notesappwithjetpackcompose.R
import com.muratdayan.notesappwithjetpackcompose.core.common.Priority
import com.muratdayan.notesappwithjetpackcompose.domain.model.Note
import com.muratdayan.notesappwithjetpackcompose.presentation.components.NoteTopBar
import com.muratdayan.notesappwithjetpackcompose.presentation.components.TextFieldItem
import com.muratdayan.notesappwithjetpackcompose.presentation.navigation.Screen
import com.muratdayan.notesappwithjetpackcompose.presentation.ui.theme.md_theme_light_tertiaryContainer
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
    var tfNotePriority by remember { mutableStateOf("LOW") }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val isTitleEmpty = remember { mutableStateOf(false) }

    val noteState = noteAddPageViewModel.noteState.collectAsStateWithLifecycle().value

    var dropControl by remember { mutableStateOf(false) }
    var selectIndex by remember { mutableIntStateOf(0) }

    val listOf = listOf("LOW", "MEDIUM", "HİGH")

    LaunchedEffect(key1 = noteId, key2 = noteState) {
        println(noteId)
        if (noteId != -1) {
            noteAddPageViewModel.getNoteById(noteId)
        }
        if (noteState.note != null) {
            tfNoteTitle = noteState.note.note_title
            tfNoteDetail = noteState.note.note_detail
            if (noteState.note.priority != null){
                tfNotePriority = noteState.note.priority.toString()
            }
            println(tfNoteTitle)
        }
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
                actionMainHandler = {
                    println("main handler")
                },
                actionSecondHandler = {
                    println("second handler")
                    scope.launch {
                        if (tfNoteTitle.isNotEmpty()) {
                            val notePriority = controlPriority(tfNotePriority)
                            if (noteId == -1) {

                                val newNote = Note(
                                    note_id = null,
                                    tfNoteTitle,
                                    tfNoteDetail,
                                    LocalDate.now().toString(),
                                    notePriority
                                )
                                noteAddPageViewModel.addNote(newNote)
                            } else {
                                val updatedNote = Note(
                                    noteId,
                                    tfNoteTitle,
                                    tfNoteDetail,
                                    LocalDate.now().toString(),
                                    notePriority
                                )
                                noteAddPageViewModel.updateNote(updatedNote)
                            }
                            navController.navigate(Screen.MainScreen.route)
                        } else {
                            isTitleEmpty.value = true
                            println("tftitle boş")
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
        content = { it ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(md_theme_light_tertiaryContainer)
                    .padding(top = it.calculateTopPadding(), start = 20.dp, end = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,

                ) {

                TextFieldItem(
                    value = tfNoteTitle,
                    onValueChange = { value ->
                        isTitleEmpty.value = false
                        tfNoteTitle = value

                    },
                    labelId = R.string.noteTitle,
                    maxlines = 1,
                )
                TextFieldItem(
                    value = tfNoteDetail,
                    onValueChange = { value ->
                        tfNoteDetail = value
                    },
                    labelId = R.string.noteDetail,
                    maxlines = 1,
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextFieldItem(
                        value = tfNotePriority,
                        onValueChange = { value ->
                            tfNotePriority = value
                        },
                        labelId = R.string.note_priority,
                        maxlines = 1,
                        trailingIcon = {
                            IconButton(onClick = {
                                dropControl = true
                            }) {
                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = null,
                                    tint = Color.White
                                )
                            }
                        }
                    )
                    DropdownMenu(
                        expanded = dropControl,
                        onDismissRequest = {
                            dropControl = false
                        }) {

                        listOf.forEachIndexed { index, s ->
                            DropdownMenuItem(
                                text = {
                                    Text(text = s)
                                },
                                onClick = {
                                    selectIndex = index
                                    tfNotePriority = listOf[selectIndex]
                                    dropControl = false
                                }
                            )
                        }
                    }
                }


            }
        }
    )
}

fun controlPriority(tfNotePriority: String): Priority{
    when(tfNotePriority){
        "LOW"->{
            return Priority.LOW
        }
        "MEDIUM"->{
            return Priority.MEDIUM
        }
        else->{
            return Priority.HIGH
        }
    }
}

@Composable
fun CustomDropDownMenu(
    dropList: List<String>,

    ) {
    var dropControl by remember { mutableStateOf(false) }
    var selectIndex by remember { mutableIntStateOf(0) }

    OutlinedCard(
        modifier = Modifier
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .wrapContentWidth()
                .padding(5.dp)
                .height(50.dp)
                .clickable {
                    dropControl = true
                },
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = dropList[selectIndex])
            Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
        }
        DropdownMenu(
            expanded = dropControl,
            onDismissRequest = {
                dropControl = false
            },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            dropList.forEachIndexed { index, s ->
                DropdownMenuItem(
                    text = {
                        Text(text = s)
                    },
                    onClick = {
                        dropControl = false
                        selectIndex = index
                    }
                )
            }
        }
    }


}



