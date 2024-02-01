package com.example.thelessonsapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.thelessonsapp.model.Lesson
import com.example.thelessonsapp.repository.LessonRepository
import kotlinx.coroutines.launch

class LessonViewModel(app: Application, private val lessonRepository: LessonRepository): AndroidViewModel(app) {

    fun addLesson(lesson: Lesson) =
        viewModelScope.launch {
            lessonRepository.insertLesson(lesson)
        }

    fun deleteLesson(lesson: Lesson) =
        viewModelScope.launch {
            lessonRepository.deleteLesson(lesson)
        }

    fun updateLesson(lesson: Lesson) =
        viewModelScope.launch {
            lessonRepository.updateLesson(lesson)
        }

    fun getAllLessons() = lessonRepository.getAllLessons()

    fun searchLesson(query: String?) =
        lessonRepository.searchLesson(query)

}