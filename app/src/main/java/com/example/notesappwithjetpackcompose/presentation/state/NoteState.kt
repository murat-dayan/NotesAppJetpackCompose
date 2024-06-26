package com.example.notesappwithjetpackcompose.presentation.state

import com.example.notesappwithjetpackcompose.domain.model.Note

data class NoteState(
    val note: Note? = null,
    val errorMsg: String ?= "",
    val isLoading : Boolean = false
)