package com.example.thelessonsapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "lessons")
@Parcelize
data class Lesson(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val lessonTile: String,
    val lessonDesc: String
): Parcelable
