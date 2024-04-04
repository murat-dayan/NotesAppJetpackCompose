package com.example.notesappwithjetpackcompose.presentation.scenes.add_note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesappwithjetpackcompose.core.common.Resource
import com.example.notesappwithjetpackcompose.domain.model.Note
import com.example.notesappwithjetpackcompose.domain.use_case.AddNoteUseCase
import com.example.notesappwithjetpackcompose.domain.use_case.DeleteNoteUseCase
import com.example.notesappwithjetpackcompose.domain.use_case.UpdateNoteUseCase
import com.example.notesappwithjetpackcompose.presentation.state.CRUDState
import com.example.notesappwithjetpackcompose.presentation.state.NoteState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class NoteAddPageViewModel @Inject constructor(
    private val addNoteUseCase: AddNoteUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
) : ViewModel(){

    private val _noteState = MutableStateFlow(NoteState())
    val noteState : StateFlow<NoteState>
        get() = _noteState

    private val _crudState = MutableStateFlow(CRUDState())
    val crudState: StateFlow<CRUDState>
        get() = _crudState

    fun addNote(note:Note){
        addNoteUseCase(note).onEach {result->
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

    fun updateNote(note:Note){
        updateNoteUseCase(note).onEach {result->
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