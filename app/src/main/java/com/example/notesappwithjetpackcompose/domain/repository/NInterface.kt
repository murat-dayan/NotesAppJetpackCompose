package com.example.notesappwithjetpackcompose.domain.repository

import com.example.notesappwithjetpackcompose.core.common.Resource
import com.example.notesappwithjetpackcompose.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NInterface {

    fun allNotes(): Flow<Resource<List<Note>>>

    fun searchNotes(searchedText:String): Flow<Resource<List<Note>>>

    fun getNoteById(noteId:Int): Flow<Resource<Note>>

    suspend fun addNote(note: Note)

    suspend fun updateNote(note: Note)

    suspend fun deleteNote(note: Note)
}