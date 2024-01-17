package com.example.notesappwithjetpackcompose.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesappwithjetpackcompose.entity.Note
import com.example.notesappwithjetpackcompose.repo.NotesDaoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteAddPageViewModel @Inject constructor(var notesDaoRepo:NotesDaoRepository) : ViewModel(){

    private val _note = MutableLiveData<Note?>(null)
    val note : LiveData<Note?> = _note

    fun saveNote(note_title:String,note_detail:String, note_date:String){
        notesDaoRepo.addNote(note_title,note_detail, note_date)
    }

    fun updateNote(note_id:Int,note_title:String,note_detail:String, note_date:String){
        notesDaoRepo.updateNote(note_id,note_title,note_detail,note_date)
    }

    fun getNoteById(noteId: Int){
        viewModelScope.launch {
            _note.value = notesDaoRepo.getNoteById(noteId)
        }
    }

}