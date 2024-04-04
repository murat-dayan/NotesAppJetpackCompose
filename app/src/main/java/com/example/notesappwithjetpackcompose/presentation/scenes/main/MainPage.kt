package com.example.notesappwithjetpackcompose.presentation.scenes.main

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.notesappwithjetpackcompose.R
import com.example.notesappwithjetpackcompose.presentation.components.NoteCard
import com.example.notesappwithjetpackcompose.presentation.components.NoteTopBar
import com.example.notesappwithjetpackcompose.presentation.ui.theme.md_theme_light_tertiaryContainer
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun MainPage(navController: NavController) {

    val viewmodel : MainPageViewModel = hiltViewModel()
    val notesList = viewmodel.notesList.observeAsState(listOf())
    val isSearching = remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current


    LaunchedEffect(key1 = true){
        viewmodel.loadNotes()
    }

    Scaffold (
        topBar = {
            NoteTopBar(
                title = stringResource(id = R.string.app_name),
                isSearching = isSearching.value,
                isCanBack = false,
                searchingHandler = {
                    viewmodel.searchNotes(it)
                },
                actionMainHandler = {
                    isSearching.value = false
                    viewmodel.loadNotes()
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
                    //navController.navigate("settings_page")
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
                LazyColumn(){
                    items(
                        count = notesList.value.count(),
                        itemContent = {
                            val note = notesList.value[it]

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
                                            viewmodel.deleteNote(note.note_id)
                                        }
                                    }
                                },
                                onClickNote = {
                                    navController.navigate("note_add_page/${note.note_id}")
                                }
                                )
                        }
                    )
                }
            }

        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("note_add_page/${-1}")
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
        bottomBar = {

        }
    )
}