package com.example.myworkoutroutine.database.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myworkoutroutine.database.dao.MuscleGroupDao
import com.example.myworkoutroutine.database.entity.Exercise
import com.example.myworkoutroutine.database.entity.MuscleGroupModel
import com.example.myworkoutroutine.database.entity.ToolbarImageEntity

@Database(entities = [MuscleGroupModel::class, Exercise::class, ToolbarImageEntity::class], version = 5)
abstract class MuscleGroupDatabase: RoomDatabase() {

    abstract fun muscleGroupDao(): MuscleGroupDao

    companion object {
        @Volatile
        private var instance: MuscleGroupDatabase? = null
        private var LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also{ instance = it}
        }

        private fun buildDatabase(context: Context): MuscleGroupDatabase{
            return Room.databaseBuilder(context.applicationContext,
            MuscleGroupDatabase::class.java,
            "muscleGroup.db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
        }

    }

}