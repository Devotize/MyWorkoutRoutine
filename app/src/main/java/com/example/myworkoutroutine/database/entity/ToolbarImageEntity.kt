package com.example.myworkoutroutine.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "toolbar_image")
data class ToolbarImageEntity(
    @PrimaryKey(autoGenerate = false)
    var id: Int,
    var uri: String
) {
}