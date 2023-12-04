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

    @Insert
    suspend fun addNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

}