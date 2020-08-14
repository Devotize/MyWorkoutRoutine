package com.example.myworkoutroutine.database.entity

import android.graphics.drawable.Drawable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "muscleGroupModel")
data class MuscleGroupModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var day: String,
    var muscleName: String
) {
}