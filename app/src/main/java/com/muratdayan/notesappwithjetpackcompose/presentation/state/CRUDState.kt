package com.muratdayan.notesappwithjetpackcompose.presentation.state

data class CRUDState(
    val resultText:Int ? = 0,
    val errorMsg: String ?= "",
    val isLoading : Boolean = false
)
