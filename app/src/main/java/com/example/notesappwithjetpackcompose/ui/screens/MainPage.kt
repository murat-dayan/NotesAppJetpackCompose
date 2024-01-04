package com.example.notesappwithjetpackcompose.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.notesappwithjetpackcompose.R
import com.example.notesappwithjetpackcompose.ui.components.NoteCard
import com.example.notesappwithjetpackcompose.ui.components.NoteTopBar
import com.example.notesappwithjetpackcompose.ui.theme.md_theme_light_primary
import com.example.notesappwithjetpackcompose.ui.theme.md_theme_light_tertiaryContainer
import com.example.notesappwithjetpackcompose.viewmodel.MainPageViewModel
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun MainPage(navController: NavController) {

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
            NoteTopBar(
                title = "My Notes",
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
                leftActionHandler = {}
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

                            NoteCard(
                                note = note,
                                onDeleteNote = {
                                    scope.launch {
                                        val sb = snackbarHostState.showSnackbar(
                                            message = "${note.note_title} note will be deleted?",
                                            actionLabel = "YES"
                                        )
                                        if (sb == SnackbarResult.ActionPerformed){
                                            viewmodel.deleteNote(note.note_id)
                                        }
                                    }
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
                    navController.navigate("note_add_page")
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
        }
    )
}