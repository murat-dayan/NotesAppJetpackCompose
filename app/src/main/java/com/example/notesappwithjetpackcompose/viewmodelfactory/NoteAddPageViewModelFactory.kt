package com.example.notesappwithjetpackcompose.viewmodelfactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notesappwithjetpackcompose.viewmodel.NoteAddPageViewModel

class NoteAddPageViewModelFactory(var application: Application): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NoteAddPageViewModel(application) as T
    }

}