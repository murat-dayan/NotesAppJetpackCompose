package com.example.notesappwithjetpackcompose.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.notesappwithjetpackcompose.entity.Note

@Database(entities = [Note::class], version = 3)
abstract class RmDatabase : RoomDatabase() {
    abstract fun notesDao():NotesDao
}