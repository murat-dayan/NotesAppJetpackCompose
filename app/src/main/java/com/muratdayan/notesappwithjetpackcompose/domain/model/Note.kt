package com.muratdayan.notesappwithjetpackcompose.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.muratdayan.notesappwithjetpackcompose.core.common.Priority

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("note_id") val note_id:Int?,
    @ColumnInfo("note_title") val note_title:String,
    @ColumnInfo("note_detail") val note_detail:String,
    @ColumnInfo("note_date") val note_date:String,
    @ColumnInfo("priority") val priority: Priority? = Priority.LOW,


    )
