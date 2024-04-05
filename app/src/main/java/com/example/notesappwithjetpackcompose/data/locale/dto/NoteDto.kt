package com.example.notesappwithjetpackcompose.data.locale.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.notesappwithjetpackcompose.core.common.Priority

@Entity(tableName = "notes")
data class NoteDto(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("note_id") var note_id:Int,
    @ColumnInfo("note_title") var note_title:String,
    @ColumnInfo("note_detail") var note_detail:String,
    @ColumnInfo("note_date") var note_date:String,
    @ColumnInfo("priority") val priority: Priority? = Priority.LOW,
    )
