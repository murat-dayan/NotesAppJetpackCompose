package com.example.notesappwithjetpackcompose.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.notesappwithjetpackcompose.entity.Note
import com.example.notesappwithjetpackcompose.repo.NotesDaoRepository

class MainPageViewModel(application: Application) : AndroidViewModel(application) {

    val notesDaoRepo = NotesDaoRepository(application)

    var notesList = MutableLiveData<List<Note>>()

    init {
        loadNotes()
        notesList = notesDaoRepo.bringNotesList()
    }

    fun loadNotes(){
        notesDaoRepo.getAllNotes()
    }

    fun deleteNote(note_id:Int){
        notesDaoRepo.deleteNote(note_id)
    }


}