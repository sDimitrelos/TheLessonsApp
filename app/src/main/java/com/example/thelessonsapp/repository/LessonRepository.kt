package com.example.thelessonsapp.repository

import com.example.thelessonsapp.database.LessonDatabase
import com.example.thelessonsapp.model.Lesson

class LessonRepository(private val db:LessonDatabase) {

    suspend fun insertLesson(lesson: Lesson) = db.getLessonDao().insertLesson(lesson)
    suspend fun deleteLesson(lesson: Lesson) = db.getLessonDao().deleteLesson(lesson)
    suspend fun updateLesson(lesson: Lesson) = db.getLessonDao().updateLesson(lesson)

    fun getAllLessons() = db.getLessonDao().getAllLessons()
    fun searchLesson(query: String?) = db.getLessonDao().searchLesson(query)
}