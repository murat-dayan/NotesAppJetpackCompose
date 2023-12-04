package com.example.notesappwithjetpackcompose.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.notesappwithjetpackcompose.entity.Note
import com.example.notesappwithjetpackcompose.repo.NotesDaoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class NoteDetailViewModel(application: Application) : AndroidViewModel(application) {
    val notesDaoRepo = NotesDaoRepository(application)

    fun updateNote(note_id:Int,note_title:String,note_detail:String, note_date:String){
       notesDaoRepo.updateNote(note_id,note_title,note_detail,note_date)
    }

}