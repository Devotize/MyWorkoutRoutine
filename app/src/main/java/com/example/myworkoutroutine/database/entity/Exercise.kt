package com.example.myworkoutroutine.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Blob

@Entity(tableName = "exercise")
class Exercise(
    @PrimaryKey
    val name: String,
    val muscleGroup: String,
    val duration: Int,
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    val image: ByteArray
) {

}