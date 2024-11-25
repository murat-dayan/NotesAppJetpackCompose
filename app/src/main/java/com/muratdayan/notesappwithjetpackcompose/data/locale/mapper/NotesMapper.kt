package com.muratdayan.notesappwithjetpackcompose.data.locale.mapper

import com.muratdayan.notesappwithjetpackcompose.data.locale.dto.NoteDto
import com.muratdayan.notesappwithjetpackcompose.domain.model.Note

fun NoteDto.toDomainNote() : Note{
    return Note(
        note_id, note_title, note_detail, note_date,priority
    )
}