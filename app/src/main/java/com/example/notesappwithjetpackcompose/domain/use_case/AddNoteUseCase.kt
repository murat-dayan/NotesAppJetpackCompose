package com.example.notesappwithjetpackcompose.domain.use_case

import com.example.notesappwithjetpackcompose.domain.model.Note
import com.example.notesappwithjetpackcompose.domain.repository.NInterface
import javax.inject.Inject

class AddNoteUseCase @Inject constructor(
    private val nInterface: NInterface
) {

    operator fun invoke(note:Note) = nInterface.addNote(note)
}