package com.muratdayan.notesappwithjetpackcompose.domain.use_case

import com.muratdayan.notesappwithjetpackcompose.domain.model.Note
import com.muratdayan.notesappwithjetpackcompose.domain.repository.NInterface
import javax.inject.Inject

class AddNoteUseCase @Inject constructor(
    private val nInterface: NInterface
) {

    suspend operator fun invoke(note:Note) = nInterface.addNote(note)
}