package com.example.notesappwithjetpackcompose.presentation.components

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.notesappwithjetpackcompose.R
import com.example.notesappwithjetpackcompose.presentation.ui.theme.NotesAppWithJetpackComposeTheme

@SuppressLint("ResourceType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldItem(
    value:String,
    onValueChange: (String) -> Unit,
    @DrawableRes labelId:Int,
    maxlines:Int,
    trailingIcon: @Composable (() -> Unit)? = null,
    enabled:Boolean = true
){
    var textFieldValueState by remember { mutableStateOf(TextFieldValue(text = value)) }
    val textFieldValue = textFieldValueState.copy(text = value)
    TextField(
        value =textFieldValue,
        onValueChange = {newValue->
            textFieldValueState = newValue
            onValueChange(textFieldValueState.text)
        },
        label = { Text(
            text = stringResource(id = labelId),
            color = Color.White
        ) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp),
        maxLines = maxlines,
        textStyle = TextStyle(
            color = Color.White
        ),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = MaterialTheme.colorScheme.tertiary,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        trailingIcon = { trailingIcon?.let { it() } },
        enabled = enabled
    )
}

@SuppressLint("ResourceType")
@Preview(showBackground = true)
@Composable
fun TextFieldItemPreview(){
    NotesAppWithJetpackComposeTheme {
        TextFieldItem(
            value = "",
            onValueChange = {},
            labelId = R.string.noteTitle,
            maxlines = 1,
            trailingIcon = {
                Icon(imageVector = Icons.Default.Favorite, contentDescription = null)
            }
        )
    }
}