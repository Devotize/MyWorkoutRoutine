package com.example.myworkoutroutine.database.dao

import androidx.room.*
import com.example.myworkoutroutine.database.entity.Exercise
import com.example.myworkoutroutine.database.entity.MuscleGroupModel
import com.example.myworkoutroutine.database.entity.ToolbarImageEntity

@Dao
interface MuscleGroupDao {

    //muscles
    @Query("SELECT * FROM muscleGroupModel")
    fun getAllSavedMusclesGroup(): List<MuscleGroupModel>

    @Query("SELECT * FROM muscleGroupModel WHERE day = :currentDay")
    fun getAllMusclesByDay(currentDay: String): List<MuscleGroupModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNewMuscleGroup(muscleGroupModel: MuscleGroupModel)

    @Delete()
    fun deleteMuscleGroup(model: MuscleGroupModel)

    //exercises
    @Query("SELECT * FROM exercise")
    fun getAllExercises(): List<Exercise>

    @Query("SELECT * FROM exercise WHERE muscleGroup = :muscleName")
    fun getExercisesByMuscles(muscleName: String): List<Exercise>

    @Insert(onConflict= OnConflictStrategy.REPLACE)
    fun addExercises(exercise: Exercise)

    @Delete
    fun deleteExercise(exercise: Exercise)

    //toolbarImage
    @Query("SELECT * FROM toolbar_image")
    fun getToolbarImage(): List<ToolbarImageEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertToolbarImage(image: ToolbarImageEntity)

    @Query("DELETE FROM toolbar_image")
    fun deleteToolbarImage()

}