package com.example.notesappwithjetpackcompose.presentation.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.notesappwithjetpackcompose.R
import com.example.notesappwithjetpackcompose.presentation.ui.components.NoteTopBar
import com.example.notesappwithjetpackcompose.presentation.ui.theme.NotesAppWithJetpackComposeTheme
import com.example.notesappwithjetpackcompose.presentation.ui.theme.md_theme_light_tertiaryContainer

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsPage(navController: NavController){

    val chosenLanguage = remember{ mutableStateOf("") }
    val isOpenLangMenu = remember{ mutableStateOf(false) }


    Scaffold (
        topBar = {
                 NoteTopBar(
                     title = stringResource(id = R.string.settingsLabel),
                     isSearching = false,
                     isCanBack = true,
                     searchingHandler = {},
                     mainActionIcon = R.drawable.settings_icon,
                     actionMainHandler = { /*TODO*/ },
                     actionSecondHandler = { /*TODO*/ },
                     leftActionHandler = { navController.navigate("main_page") },
                     menuAvailable = false,
                     leftIcon = R.drawable.back_icon
                 ) {

                 }
        },
        content = { it ->
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .background(md_theme_light_tertiaryContainer)
                    .padding(top = it.calculateTopPadding(), start = 20.dp, end = 20.dp),
            ){
                    TextField(
                        value = chosenLanguage.value ,
                        readOnly = true,
                        label = {
                            Text(text = "Change Language")
                        },
                        onValueChange = {},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp),
                        trailingIcon = {
                            Box {
                                IconButton(
                                    onClick = {
                                        isOpenLangMenu.value = true
                                    }
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.arrow_down_icon) ,
                                        contentDescription = null)
                                }

                                DropDownMenuContent(
                                    expanded = isOpenLangMenu.value,
                                    onDismissRequest = { isOpenLangMenu.value = false },
                                    onItemClick = {lang->
                                        chosenLanguage.value = lang
                                    }
                                )
                            }
                        }
                    )


            }
        },
        bottomBar = {


        }
    )
}



@Composable
fun DropDownMenuContent(
    expanded:Boolean,
    onDismissRequest: ()->Unit,
    onItemClick: (lang:String)->Unit
){
    val languageOptions = listOf<String>("Türkçe","English")

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest
    ) {
        languageOptions.forEach { langOption->
            DropdownMenuItem(
                text = { Text(text = langOption)},
                onClick = { onItemClick(langOption) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsPagePreview(){
    NotesAppWithJetpackComposeTheme {
        SettingsPage(rememberNavController())
    }
}

