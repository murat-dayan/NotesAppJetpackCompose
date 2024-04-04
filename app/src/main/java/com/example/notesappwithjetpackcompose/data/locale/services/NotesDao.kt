package com.example.notesappwithjetpackcompose.data.locale.services

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.notesappwithjetpackcompose.data.locale.dto.NoteDto
import com.example.notesappwithjetpackcompose.domain.model.Note

@Dao
interface NotesDao {

    @Query("SELECT*FROM notes")
    suspend fun allNotes():List<NoteDto>

    @Query("SELECT * FROM notes WHERE note_title LIKE '%' || :searchedText || '%'")
    suspend fun searchNotes(searchedText:String): List<NoteDto>

    @Query("SELECT * FROM notes WHERE note_id = :noteId")
    suspend fun getNoteById(noteId:Int): NoteDto?

    @Insert
    suspend fun addNote(note: Note) : String

    @Update
    suspend fun updateNote(note: Note) : String

    @Delete
    suspend fun deleteNote(note: Note) : String

}