package com.muratdayan.notesappwithjetpackcompose.presentation.state

import com.muratdayan.notesappwithjetpackcompose.domain.model.Note

data class NoteState(
    val note: Note? = null,
    val errorMsg: String ?= "",
    val isLoading : Boolean = false
)