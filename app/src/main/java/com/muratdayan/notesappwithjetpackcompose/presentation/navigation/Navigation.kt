package com.muratdayan.notesappwithjetpackcompose.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.muratdayan.notesappwithjetpackcompose.presentation.scenes.add_note.NoteAddPage
import com.muratdayan.notesappwithjetpackcompose.presentation.scenes.add_note.NoteAddPageViewModel
import com.muratdayan.notesappwithjetpackcompose.presentation.scenes.main.MainPage
import com.muratdayan.notesappwithjetpackcompose.presentation.scenes.main.MainPageViewModel
import com.muratdayan.notesappwithjetpackcompose.presentation.scenes.settings.SettingsPage

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation(){

    val navCont = rememberNavController()

    NavHost(
        navController = navCont,
        startDestination = Screen.MainScreen.route
    ){

        composable(Screen.MainScreen.route){
            val mainPageViewModel = hiltViewModel<MainPageViewModel>()
            MainPage(navController = navCont, mainPageViewModel)
        }
        composable("${Screen.AddNoteScreen.route}/{noteId}",
            arguments = listOf(
                navArgument("noteId"){type= NavType.IntType}
            )
        ){
            val noteId= it.arguments?.getInt("noteId")!!
            val noteAddPageViewModel = hiltViewModel<NoteAddPageViewModel>()
            NoteAddPage(navController = navCont, noteId , noteAddPageViewModel )
        }
        composable(Screen.SettingsScreen.route){
            SettingsPage(navCont)
        }

    }
}