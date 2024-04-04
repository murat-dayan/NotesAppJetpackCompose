package com.example.notesappwithjetpackcompose.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.notesappwithjetpackcompose.presentation.scenes.add_note.NoteAddPage
import com.example.notesappwithjetpackcompose.presentation.scenes.main.MainPage
import com.example.notesappwithjetpackcompose.presentation.scenes.settings.SettingsPage

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation(){

    val navCont = rememberNavController()

    NavHost(
        navController = navCont,
        startDestination = Screen.MainScreen.route
    ){

        composable(Screen.MainScreen.route){
            MainPage(navController = navCont)
        }
        composable("${Screen.AddNoteScreen.route}/{noteId}",
            arguments = listOf(
                navArgument("noteId"){type= NavType.IntType}
            )
        ){
            val noteId= it.arguments?.getInt("noteId")!!
            NoteAddPage(navController = navCont, noteId)
        }
        composable(Screen.SettingsScreen.route){
            SettingsPage(navCont)
        }

    }
}