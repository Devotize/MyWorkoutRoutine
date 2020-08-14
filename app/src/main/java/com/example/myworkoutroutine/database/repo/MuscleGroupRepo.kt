package com.example.myworkoutroutine.database.repo

import android.content.Context
import com.example.myworkoutroutine.database.db.MuscleGroupDatabase
import com.example.myworkoutroutine.database.entity.Exercise
import com.example.myworkoutroutine.database.entity.MuscleGroupModel
import com.example.myworkoutroutine.database.entity.ToolbarImageEntity

class MuscleGroupRepo(private val context: Context) {

    private var db = MuscleGroupDatabase.invoke(context)
    private var dao = db.muscleGroupDao()

    fun getAllSavedMusclesGroups(): List<MuscleGroupModel> {
        return dao.getAllSavedMusclesGroup()
    }

    fun getMusclesByDay(day: String): List<MuscleGroupModel> {
        return dao.getAllMusclesByDay(day)
    }

    fun insertNewMuscleGroup(muscleGroup: MuscleGroupModel) {
         dao.addNewMuscleGroup(muscleGroup)
    }

    fun deleteMuscleGroup(muscleGroup: MuscleGroupModel) {
        dao.deleteMuscleGroup(muscleGroup)
    }

    fun getAllExercises(): List<Exercise> = dao.getAllExercises()

    fun getExercisesByMuscle(muscleName: String): List<Exercise> =
        dao.getExercisesByMuscles(muscleName)

    fun addExercise(exercise: Exercise) = dao.addExercises(exercise)

    fun deleteExercise(exercise: Exercise) = dao.deleteExercise(exercise)

    fun getToolbarImage(): List<ToolbarImageEntity> {
        return dao.getToolbarImage()
    }

    fun insertToolbarImage(image: ToolbarImageEntity) {
        dao.insertToolbarImage(image)
    }

    fun deleteToolbarImage() {
        dao.deleteToolbarImage()
    }

}