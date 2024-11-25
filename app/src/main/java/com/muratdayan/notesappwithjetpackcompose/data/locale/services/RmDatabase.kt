package com.muratdayan.notesappwithjetpackcompose.data.locale.services

import androidx.room.Database
import androidx.room.RoomDatabase
import com.muratdayan.notesappwithjetpackcompose.domain.model.Note

@Database(entities = [Note::class], version = 1)
abstract class RmDatabase : RoomDatabase() {
    abstract fun notesDao(): NotesDao
}