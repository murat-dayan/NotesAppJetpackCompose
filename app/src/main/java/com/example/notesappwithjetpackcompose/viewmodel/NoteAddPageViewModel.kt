package com.example.notesappwithjetpackcompose.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.notesappwithjetpackcompose.repo.NotesDaoRepository

class NoteAddPageViewModel(application: Application) : AndroidViewModel(application) {
    val notesDaoRepo = NotesDaoRepository(application)

    fun saveNote(note_title:String,note_detail:String, note_date:String){
        notesDaoRepo.addNote(note_title,note_detail, note_date)
    }

}