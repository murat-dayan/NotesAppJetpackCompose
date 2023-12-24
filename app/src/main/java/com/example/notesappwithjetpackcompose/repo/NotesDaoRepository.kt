package com.example.notesappwithjetpackcompose.repo

import androidx.lifecycle.MutableLiveData
import com.example.notesappwithjetpackcompose.entity.Note
import com.example.notesappwithjetpackcompose.room.NotesDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class NotesDaoRepository @Inject constructor(private val notesDao: NotesDao) {

    var notesList = MutableLiveData<List<Note>>()

    init {
        notesList = MutableLiveData<List<Note>>()
    }

    fun bringNotesList():MutableLiveData<List<Note>>{
        return notesList
    }

    fun getAllNotes(){
        val job:Job = CoroutineScope(Dispatchers.Main).launch {
            notesList.value = notesDao.allNotes()
        }

    }

    fun searchNotes(searchedText:String){
        val job:Job = CoroutineScope(Dispatchers.Main).launch {
            notesList.value = notesDao.searchNotes(searchedText)
        }
    }

    fun addNote(note_title: String, note_detail: String, note_date:String){
        val job:Job = CoroutineScope(Dispatchers.Main).launch {
            val newNote = Note(0,note_title,note_detail, note_date)
            notesDao.addNote(newNote)
        }
    }

    fun updateNote(note_id:Int, note_title:String, note_detail:String,note_date: String){
        val job:Job = CoroutineScope(Dispatchers.Main).launch {
            val updatingNote = Note(note_id,note_title,note_detail, note_date)
            notesDao.updateNote(updatingNote)
        }
    }

    fun deleteNote(note_id:Int){
        val job:Job = CoroutineScope(Dispatchers.Main).launch {
            val deletingNote = Note(note_id,"","","")
            notesDao.deleteNote(deletingNote)
            getAllNotes()
        }
    }
}