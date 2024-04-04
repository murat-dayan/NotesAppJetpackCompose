package com.example.notesappwithjetpackcompose.domain.repository

import com.example.notesappwithjetpackcompose.core.common.Resource
import com.example.notesappwithjetpackcompose.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NInterface {

    fun allNotes(): Flow<Resource<List<Note>>>

    fun searchNotes(searchedText:String): Flow<Resource<List<Note>>>

    fun getNoteById(noteId:Int): Flow<Resource<Note>>

    fun addNote(note: Note) : Flow<Resource<String>>

    fun updateNote(note: Note) : Flow<Resource<String>>

    fun deleteNote(note: Note) : Flow<Resource<String>>
}