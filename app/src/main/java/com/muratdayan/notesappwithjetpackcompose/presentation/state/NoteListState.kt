package com.muratdayan.notesappwithjetpackcompose.presentation.state

import com.muratdayan.notesappwithjetpackcompose.domain.model.Note

data class NoteListState(
    val notes: List<Note>? = emptyList(),
    val errorMsg: String ?= "",
    val isLoading : Boolean = false
)
