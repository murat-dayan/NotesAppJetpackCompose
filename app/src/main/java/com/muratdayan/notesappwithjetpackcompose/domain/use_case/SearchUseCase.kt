package com.muratdayan.notesappwithjetpackcompose.domain.use_case

import com.muratdayan.notesappwithjetpackcompose.domain.repository.NInterface
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val nInterface: NInterface
) {

    operator fun invoke(searchQuery:String) = nInterface.searchNotes(searchQuery)
}