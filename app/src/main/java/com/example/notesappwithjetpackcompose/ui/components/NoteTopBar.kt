package com.example.notesappwithjetpackcompose.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.notesappwithjetpackcompose.R
import com.example.notesappwithjetpackcompose.ui.theme.NotesAppWithJetpackComposeTheme
import com.example.notesappwithjetpackcompose.ui.theme.md_theme_light_primary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteTopBar(
    title:String,
    isSearching: Boolean,
    isCanBack: Boolean,
    searchingHandler: (searchedText: String) -> Unit,
    @DrawableRes mainActionIcon: Int,
    @DrawableRes secondActionIcon: Int? = null,
    @DrawableRes leftIcon: Int?=null,
    actionMainHandler: () -> Unit,
    actionSecondHandler: () -> Unit,
    leftActionHandler: ()->Unit
    ) {

    val tfSearch = remember { mutableStateOf("") }
    TopAppBar(
        title = {

            if (isSearching) {
                TextField(
                    value = tfSearch.value,
                    onValueChange = {
                        tfSearch.value = it
                        searchingHandler(it)
                    },
                    placeholder = {
                        Text(
                            text = "Search...",
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 5.dp)
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = md_theme_light_primary,
                        textColor = Color.White,
                        focusedLabelColor = Color.Red,
                        unfocusedLabelColor = Color.Blue
                    ),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Search
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                )
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,

                ) {
                    if (isCanBack) {
                        IconButton(
                            onClick = {
                                leftActionHandler()
                            }
                        ) {
                            Icon(
                                painter = painterResource(id = leftIcon!!),
                                contentDescription = "back",
                                tint = Color.White,
                                modifier = Modifier.size(40.dp)
                            )
                        }
                    }
                    Text(
                        text = title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }

        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = md_theme_light_primary,
            titleContentColor = Color.White
        ),

        actions = {
            if (isSearching) {
                IconButton(
                    onClick = {
                        tfSearch.value = ""
                        actionMainHandler()
                    }
                ) {
                    Icon(
                        painter = painterResource(id = secondActionIcon!!),
                        contentDescription = "search",
                        tint = Color.White
                    )
                }
            } else {
                IconButton(
                    onClick = {
                        tfSearch.value = ""
                        actionSecondHandler()
                    }
                ) {
                    Icon(
                        painter = painterResource(id = mainActionIcon),
                        contentDescription = "close",
                        tint = Color.White
                    )
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun NoteTopBarPreview() {
    NotesAppWithJetpackComposeTheme {
        NoteTopBar(
            title = "Notes",
            isSearching = false,
            isCanBack = true,
            actionMainHandler = {},
            actionSecondHandler = {},
            searchingHandler = {},
            mainActionIcon = R.drawable.search_icon,
            secondActionIcon = R.drawable.close__icon,
            leftActionHandler = {},
            leftIcon = R.drawable.back_icon
        )
    }
}