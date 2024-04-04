package com.example.notesappwithjetpackcompose.presentation

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.notesappwithjetpackcompose.presentation.ui.screens.MainPage
import com.example.notesappwithjetpackcompose.presentation.ui.screens.NoteAddPage
import com.example.notesappwithjetpackcompose.presentation.ui.screens.SettingsPage
import com.example.notesappwithjetpackcompose.presentation.ui.theme.NotesAppWithJetpackComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotesAppWithJetpackComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHosting()
                }
            }
        }
    }
}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavHosting(){
    val navCont = rememberNavController()

    NavHost(
        navController = navCont,
        startDestination = "main_page"){

        composable("main_page"){
            MainPage(navController = navCont)
        }
        composable("note_add_page/{noteId}",
            arguments = listOf(
                navArgument("noteId"){type= NavType.IntType}
            )
        ){
            val noteId= it.arguments?.getInt("noteId")!!
            NoteAddPage(navController = navCont, noteId)
        }
        composable("settings_page"){
            SettingsPage(navCont)
        }

    }
}



