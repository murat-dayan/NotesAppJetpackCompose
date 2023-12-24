package com.example.notesappwithjetpackcompose.viewmodel

import androidx.lifecycle.ViewModel
import com.example.notesappwithjetpackcompose.repo.NotesDaoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(var notesDaoRepo:NotesDaoRepository) : ViewModel() {

    fun updateNote(note_id:Int,note_title:String,note_detail:String, note_date:String){
       notesDaoRepo.updateNote(note_id,note_title,note_detail,note_date)
    }

}