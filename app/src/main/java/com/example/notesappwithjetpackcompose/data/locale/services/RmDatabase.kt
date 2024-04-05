package com.example.notesappwithjetpackcompose.data.locale.services

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.notesappwithjetpackcompose.data.locale.dto.NoteDto
import com.example.notesappwithjetpackcompose.data.locale.services.NotesDao
import com.example.notesappwithjetpackcompose.domain.model.Note

@Database(entities = [Note::class], version = 1)
abstract class RmDatabase : RoomDatabase() {
    abstract fun notesDao(): NotesDao
}