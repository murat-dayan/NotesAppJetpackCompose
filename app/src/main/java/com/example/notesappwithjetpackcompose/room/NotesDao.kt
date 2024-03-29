package com.example.notesappwithjetpackcompose.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.notesappwithjetpackcompose.entity.Note

@Dao
interface NotesDao {

    @Query("SELECT*FROM notes")
    suspend fun allNotes():List<Note>

    @Query("SELECT * FROM notes WHERE note_title LIKE '%' || :searchedText || '%'")
    suspend fun searchNotes(searchedText:String): List<Note>

    @Query("SELECT * FROM notes WHERE note_id = :noteId")
    suspend fun getNoteById(noteId:Int): Note?

    @Insert
    suspend fun addNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

}