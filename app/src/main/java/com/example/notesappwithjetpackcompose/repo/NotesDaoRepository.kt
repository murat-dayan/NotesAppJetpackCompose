package com.example.notesappwithjetpackcompose.repo

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.example.notesappwithjetpackcompose.entity.Note
import com.example.notesappwithjetpackcompose.room.RmDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.time.LocalDate

class NotesDaoRepository(var application: Application) {

    var vt:RmDatabase
    var notesList = MutableLiveData<List<Note>>()

    init {
        vt= RmDatabase.databaseAccess(application)!!
        notesList = MutableLiveData<List<Note>>()
    }

    fun bringNotesList():MutableLiveData<List<Note>>{
        return notesList
    }

    fun getAllNotes(){
        val job:Job = CoroutineScope(Dispatchers.Main).launch {
            notesList.value = vt.notesDao().allNotes()
        }

    }

    fun addNote(note_title: String, note_detail: String, note_date:String){
        val job:Job = CoroutineScope(Dispatchers.Main).launch {
            val newNote = Note(0,note_title,note_detail, note_date)
            vt.notesDao().addNote(newNote)
        }
    }

    fun updateNote(note_id:Int, note_title:String, note_detail:String,note_date: String){
        val job:Job = CoroutineScope(Dispatchers.Main).launch {
            val updatingNote = Note(note_id,note_title,note_detail, note_date)
            vt.notesDao().updateNote(updatingNote)
        }
    }

    fun deleteNote(note_id:Int){
        val job:Job = CoroutineScope(Dispatchers.Main).launch {
            val deletingNote = Note(note_id,"","","")
            vt.notesDao().deleteNote(deletingNote)
            getAllNotes()
        }
    }
}