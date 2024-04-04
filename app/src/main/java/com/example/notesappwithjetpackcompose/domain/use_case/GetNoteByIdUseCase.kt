package com.example.notesappwithjetpackcompose.domain.use_case

import com.example.notesappwithjetpackcompose.domain.repository.NInterface
import javax.inject.Inject

class GetNoteByIdUseCase @Inject constructor(
    private val nInterface: NInterface
) {

    operator fun invoke(noteId:Int) = nInterface.getNoteById(noteId)
}