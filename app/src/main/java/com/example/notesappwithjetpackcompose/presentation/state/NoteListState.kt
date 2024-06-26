package com.example.notesappwithjetpackcompose.presentation.state

import com.example.notesappwithjetpackcompose.domain.model.Note

data class NoteListState(
    val notes: List<Note>? = emptyList(),
    val errorMsg: String ?= "",
    val isLoading : Boolean = false
)
