package com.example.notesappwithjetpackcompose.data.locale.services

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.notesappwithjetpackcompose.data.locale.services.NotesDao
import com.example.notesappwithjetpackcompose.domain.model.Note

@Database(entities = [Note::class], version = 3)
abstract class RmDatabase : RoomDatabase() {
    abstract fun notesDao(): NotesDao
}