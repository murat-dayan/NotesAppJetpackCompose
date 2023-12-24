package com.example.notesappwithjetpackcompose.viewmodel

import androidx.lifecycle.ViewModel
import com.example.notesappwithjetpackcompose.repo.NotesDaoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NoteAddPageViewModel @Inject constructor(var notesDaoRepo:NotesDaoRepository) : ViewModel(){

    fun saveNote(note_title:String,note_detail:String, note_date:String){
        notesDaoRepo.addNote(note_title,note_detail, note_date)
    }

}