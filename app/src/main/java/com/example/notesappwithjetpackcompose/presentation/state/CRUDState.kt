package com.example.notesappwithjetpackcompose.presentation.state

import com.example.notesappwithjetpackcompose.domain.model.Note

data class CRUDState(
    val resultText:String ? = "",
    val errorMsg: String ?= "",
    val isLoading : Boolean = false
)
