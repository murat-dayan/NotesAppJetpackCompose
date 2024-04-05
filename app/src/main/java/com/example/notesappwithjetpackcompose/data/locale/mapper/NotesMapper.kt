package com.example.notesappwithjetpackcompose.data.locale.mapper

import com.example.notesappwithjetpackcompose.data.locale.dto.NoteDto
import com.example.notesappwithjetpackcompose.domain.model.Note

fun NoteDto.toDomainNote() : Note{
    return Note(
        note_id, note_title, note_detail, note_date,priority
    )
}