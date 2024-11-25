package com.muratdayan.notesappwithjetpackcompose.presentation.scenes.main

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.muratdayan.notesappwithjetpackcompose.R
import com.muratdayan.notesappwithjetpackcompose.domain.model.Note
import com.muratdayan.notesappwithjetpackcompose.presentation.components.NoteCard
import com.muratdayan.notesappwithjetpackcompose.presentation.components.NoteTopBar
import com.muratdayan.notesappwithjetpackcompose.presentation.navigation.Screen
import com.muratdayan.notesappwithjetpackcompose.presentation.ui.theme.md_theme_light_tertiaryContainer
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainPage(
    navController: NavController,
    mainPageViewModel: MainPageViewModel

) {

    val isSearching = remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val notesListState = mainPageViewModel.noteListState.collectAsStateWithLifecycle().value

    Scaffold (
        topBar = {
            NoteTopBar(
                title = stringResource(id = R.string.app_name),
                isSearching = isSearching.value,
                isCanBack = false,
                searchingHandler = {
                    mainPageViewModel.searchNote(it)
                },
                actionMainHandler = {
                    isSearching.value = false
                },
                actionSecondHandler = {
                    isSearching.value = true
                },
                mainActionIcon = R.drawable.search_icon,
                secondActionIcon = R.drawable.close__icon,
                leftIcon = null,
                leftActionHandler = {},
                menuAvailable = true,
                menuItemHandler = {
                    navController.navigate(Screen.SettingsScreen.route)
                },

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
                    .padding(top = innerPading.calculateTopPadding())
                ) {
                if (notesListState.notes?.isNotEmpty() == true){
                    LazyColumn(){
                        items(
                            count = notesListState.notes.count(),
                            itemContent = {
                                val note = notesListState.notes[it]

                                NoteCard(
                                    note = note,
                                    onDeleteNote = {
                                        scope.launch {
                                            val sb = snackbarHostState.showSnackbar(
                                                message = "${note.note_title} ${context.getString(R.string.deleteWarning)}",
                                                actionLabel = context.getString(R.string.yes),
                                                withDismissAction = true

                                            )
                                            if (sb == SnackbarResult.ActionPerformed){
                                                val deletedNote = Note(note.note_id,"","","")
                                                mainPageViewModel.deleteNote(deletedNote)
                                            }
                                        }
                                    },
                                    onClickNote = {
                                        navController.navigate("${Screen.AddNoteScreen.route}/${note.note_id}")
                                    }
                                )
                            }
                        )
                    }
                }
                if (notesListState.isLoading) {
                    CircularProgressIndicator()
                }

                if (!(notesListState.errorMsg.isNullOrEmpty())) {
                    println(notesListState.errorMsg)

                }
            }

        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("${Screen.AddNoteScreen.route}/${-1}")
                },
                content = {
                    Icon(
                        painter = painterResource(id = R.drawable.add_icon),
                        contentDescription = null,
                    )
                },
                modifier = Modifier
                    .clip(CircleShape)
            )
        },

    )
}