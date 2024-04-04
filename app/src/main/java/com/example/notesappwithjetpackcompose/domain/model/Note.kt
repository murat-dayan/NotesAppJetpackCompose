package com.example.notesappwithjetpackcompose.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class Note(

    val note_id:Int,
    val note_title:String,
    val note_detail:String,
    val note_date:String
)
