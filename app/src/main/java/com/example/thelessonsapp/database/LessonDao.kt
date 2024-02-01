package com.example.thelessonsapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.thelessonsapp.model.Lesson

@Dao
interface LessonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLesson(lesson: Lesson)

    @Update
    suspend fun updateLesson(lesson: Lesson)

    @Delete
    suspend fun deleteLesson(lesson: Lesson)

    @Query("SELECT * FROM LESSONS ORDER BY id DESC")
    fun getAllLessons():LiveData<List<Lesson>>

    @Query("SELECT * FROM LESSONS WHERE lessonTile LIKE :query OR lessonDesc LIKE :query")
    fun searchLesson(query: String?): LiveData<List<Lesson>>
}