package com.example.notesappwithjetpackcompose.presentation.scenes.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesappwithjetpackcompose.core.common.Resource
import com.example.notesappwithjetpackcompose.domain.model.Note
import com.example.notesappwithjetpackcompose.domain.use_case.DeleteNoteUseCase
import com.example.notesappwithjetpackcompose.domain.use_case.GetAllNotesUseCase
import com.example.notesappwithjetpackcompose.domain.use_case.SearchUseCase
import com.example.notesappwithjetpackcompose.presentation.state.CRUDState
import com.example.notesappwithjetpackcompose.presentation.state.NoteListState
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
class MainPageViewModel @Inject constructor(
    private val getAllNotesUseCase: GetAllNotesUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val searchUseCase: SearchUseCase
) : ViewModel() {

    private val _crudState = MutableStateFlow(CRUDState())
    val crudState: StateFlow<CRUDState>
        get() = _crudState

    private val _noteListState = MutableStateFlow(NoteListState())
    val noteListState : StateFlow<NoteListState>
        get() = _noteListState


    init {
        getAllNotes()
    }

    private fun getAllNotes(){
        getAllNotesUseCase().onEach { result->
            when(result){
                is Resource.Error -> {
                    _noteListState.value = NoteListState().copy(
                        errorMsg = result.msg
                    )
                }
                is Resource.Loading -> {
                    _noteListState.value = NoteListState().copy(
                        isLoading = true
                    )
                }
                is Resource.Success -> {
                    _noteListState.value = NoteListState().copy(
                        notes = result.data
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun deleteNote(note: Note){
        CoroutineScope(Dispatchers.IO).launch {
            deleteNoteUseCase(note)
        }
    }

    fun searchNote(searchQuery:String){
        searchUseCase(searchQuery = searchQuery).onEach {result->
            when(result){
                is Resource.Error -> {
                    _noteListState.value = NoteListState().copy(
                        errorMsg = result.msg
                    )
                }
                is Resource.Loading -> {
                    _noteListState.value = NoteListState().copy(
                        isLoading = true
                    )
                }
                is Resource.Success -> {
                    _noteListState.value = NoteListState().copy(
                        notes = result.data
                    )
                }
            }

        }.launchIn(viewModelScope)
    }




}