package com.muratdayan.notesappwithjetpackcompose.presentation.navigation

sealed class Screen (val route:String){

    object MainScreen : Screen("main_route")
    object AddNoteScreen : Screen("add_route")
    object SettingsScreen : Screen("settings_route")
}