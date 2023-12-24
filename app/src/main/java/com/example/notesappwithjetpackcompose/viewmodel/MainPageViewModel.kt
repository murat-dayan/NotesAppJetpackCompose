package com.example.notesappwithjetpackcompose.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.notesappwithjetpackcompose.entity.Note
import com.example.notesappwithjetpackcompose.repo.NotesDaoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainPageViewModel @Inject constructor(private val notesDaoRepo:NotesDaoRepository) : ViewModel() {


    var notesList = MutableLiveData<List<Note>>()

    init {
        loadNotes()
        notesList = notesDaoRepo.bringNotesList()
    }

    fun searchNotes(searchedText:String){
        notesDaoRepo.searchNotes(searchedText)
    }

    fun loadNotes(){
        notesDaoRepo.getAllNotes()
    }

    fun deleteNote(note_id:Int){
        notesDaoRepo.deleteNote(note_id)
    }


}