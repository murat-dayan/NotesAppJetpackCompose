package com.muratdayan.notesappwithjetpackcompose.presentation.scenes.add_note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muratdayan.notesappwithjetpackcompose.core.common.Resource
import com.muratdayan.notesappwithjetpackcompose.domain.model.Note
import com.muratdayan.notesappwithjetpackcompose.domain.use_case.AddNoteUseCase
import com.muratdayan.notesappwithjetpackcompose.domain.use_case.GetNoteByIdUseCase
import com.muratdayan.notesappwithjetpackcompose.domain.use_case.UpdateNoteUseCase
import com.muratdayan.notesappwithjetpackcompose.presentation.state.CRUDState
import com.muratdayan.notesappwithjetpackcompose.presentation.state.NoteState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteAddPageViewModel @Inject constructor(
    private val addNoteUseCase: AddNoteUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
    private val getNoteByIdUseCase: GetNoteByIdUseCase
) : ViewModel(){


    private val _crudState = MutableStateFlow(CRUDState())
    val crudState: StateFlow<CRUDState>
        get() = _crudState

    private val _noteState = MutableStateFlow(NoteState())
    val noteState : StateFlow<NoteState>
        get() = _noteState

    fun addNote(note:Note){
        CoroutineScope(Dispatchers.IO).launch {
            addNoteUseCase(note)
        }
    }

    fun updateNote(note:Note){
        CoroutineScope(Dispatchers.IO).launch {
            updateNoteUseCase(note)
        }
    }

    fun getNoteById(noteId:Int){
        getNoteByIdUseCase(noteId).onEach { result->
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
                        note = result.data
                    )
                }
            }
        }.launchIn(viewModelScope)
    }


}