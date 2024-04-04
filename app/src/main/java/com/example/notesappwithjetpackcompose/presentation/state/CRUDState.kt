package com.example.notesappwithjetpackcompose.presentation.state

import com.example.notesappwithjetpackcompose.domain.model.Note

data class CRUDState(
    val resultText:Int ? = 0,
    val errorMsg: String ?= "",
    val isLoading : Boolean = false
)
