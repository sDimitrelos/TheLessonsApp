package com.example.thelessonsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.thelessonsapp.database.LessonDatabase
import com.example.thelessonsapp.repository.LessonRepository
import com.example.thelessonsapp.viewmodel.LessonViewModel
import com.example.thelessonsapp.viewmodel.LessonViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var  lessonViewModel: LessonViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViewModel()
    }

    private fun setupViewModel(){
        val lessonRepository = LessonRepository(LessonDatabase(this))
        val viewModelProviderFactory = LessonViewModelFactory(application, lessonRepository)
        lessonViewModel = ViewModelProvider(this, viewModelProviderFactory)[LessonViewModel::class.java]
    }
}