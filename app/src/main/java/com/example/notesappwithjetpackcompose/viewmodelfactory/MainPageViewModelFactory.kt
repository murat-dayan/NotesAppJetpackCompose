package com.example.notesappwithjetpackcompose.viewmodelfactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notesappwithjetpackcompose.viewmodel.MainPageViewModel

class MainPageViewModelFactory(var application: Application): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainPageViewModel(application) as T
    }

}