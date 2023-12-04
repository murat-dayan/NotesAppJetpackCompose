package com.example.notesappwithjetpackcompose.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("note_id") var note_id:Int,
    @ColumnInfo("note_title") var note_title:String,
    @ColumnInfo("note_detail") var note_detail:String,
    @ColumnInfo("note_date") var note_date:String
)
