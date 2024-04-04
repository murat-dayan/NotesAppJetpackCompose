package com.example.notesappwithjetpackcompose.presentation.scenes.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesappwithjetpackcompose.core.common.Resource
import com.example.notesappwithjetpackcompose.domain.model.Note
import com.example.notesappwithjetpackcompose.domain.use_case.DeleteNoteUseCase
import com.example.notesappwithjetpackcompose.domain.use_case.GetAllNotesUseCase
import com.example.notesappwithjetpackcompose.presentation.state.CRUDState
import com.example.notesappwithjetpackcompose.presentation.state.NoteState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainPageViewModel @Inject constructor(
    private val getAllNotesUseCase: GetAllNotesUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase
) : ViewModel() {

    private val _crudState = MutableStateFlow(CRUDState())
    val crudState: StateFlow<CRUDState>
        get() = _crudState

    private val _noteState = MutableStateFlow(NoteState())
    val noteState : StateFlow<NoteState>
        get() = _noteState


    init {
        getAllNotes()
    }

    private fun getAllNotes(){
        getAllNotesUseCase().onEach { result->
            when(result){
                is Resource.Error -> {
                    _noteState.value = NoteState().copy(
                        errorMsg = result.msg
                    )
                }
                is Resource.Loading -> {
                    _noteState.value = NoteState().copy(
                        isLoading = true
                    )
                }
                is Resource.Success -> {
                    _noteState.value = NoteState().copy(
                        notes = result.data
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun deleteNote(note: Note){
        deleteNoteUseCase(note).onEach {result->
            when(result){
                is Resource.Error -> {
                    _crudState.value = CRUDState().copy(
                        errorMsg = result.msg
                    )
                }
                is Resource.Loading -> {
                    _crudState.value = CRUDState().copy(
                        isLoading = true
                    )
                }
                is Resource.Success -> {
                    _crudState.value = CRUDState().copy(
                        resultText = result.data
                    )
                }
            }

        }.launchIn(viewModelScope)
    }


}