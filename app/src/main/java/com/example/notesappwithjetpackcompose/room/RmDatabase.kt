package com.example.notesappwithjetpackcompose.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notesappwithjetpackcompose.entity.Note

@Database(entities = [Note::class], version = 3)
abstract class RmDatabase : RoomDatabase() {

    abstract fun notesDao():NotesDao

    companion object{
        var INSTANCE:RmDatabase?=null

        fun databaseAccess(context: Context): RmDatabase?{
            if (INSTANCE == null){
                synchronized(RmDatabase::class){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        RmDatabase::class.java,
                        "notes.sqlite"
                    ).fallbackToDestructiveMigration().build()
                }
            }
            return INSTANCE
        }
    }


}