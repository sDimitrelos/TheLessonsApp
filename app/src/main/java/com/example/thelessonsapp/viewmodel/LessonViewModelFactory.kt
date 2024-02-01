package com.example.thelessonsapp.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.thelessonsapp.repository.LessonRepository

class LessonViewModelFactory(val app:Application, private val lessonRepository: LessonRepository) :ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LessonViewModel(app, lessonRepository) as T
    }


}