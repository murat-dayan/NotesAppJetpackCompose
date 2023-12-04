package com.example.notesappwithjetpackcompose.viewmodelfactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notesappwithjetpackcompose.viewmodel.NoteDetailViewModel

class NoteDetailPageViewModelFactory(var application: Application): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NoteDetailViewModel(application) as T
    }

}