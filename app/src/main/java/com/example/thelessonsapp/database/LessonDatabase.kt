package com.example.thelessonsapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.thelessonsapp.model.Lesson

@Database(entities = [Lesson::class], version = 1)
abstract class LessonDatabase: RoomDatabase(){

    abstract fun getLessonDao(): LessonDao

    companion object{
        @Volatile
        private var instance: LessonDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?:
        synchronized(LOCK){
            instance ?:
            createDatabase(context).also{
                instance = it
            }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                LessonDatabase::class.java,
                "lesson_db"
            ).build()
    }
}