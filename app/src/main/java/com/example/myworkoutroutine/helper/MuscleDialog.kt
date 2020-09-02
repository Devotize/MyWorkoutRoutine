package com.example.myworkoutroutine.helper

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import com.example.myworkoutroutine.R
import com.example.myworkoutroutine.database.entity.MuscleGroupModel
import com.example.myworkoutroutine.database.repo.MuscleGroupRepo
import com.example.myworkoutroutine.helperЕ.MuscleGroup

class MuscleDialog(private val context: Context) {

    private val TAG = "RecyclerMuscleAdapter"

    fun showAddMusclesDialog(currentDay: String, callback: (MuscleGroupModel?) -> Unit) {
        Log.d("Callback", "Show dialog")
        val alertBuilder = AlertDialog.Builder(context)
        val items: Array<String> = Array(MuscleGroup.values().size) { it ->
            MuscleGroup.values().get(it).muscleName
        }
        alertBuilder.setTitle("Add Muscle")

        Log.d(TAG, "Items: $items")

        alertBuilder.setItems(items) { dialog: DialogInterface?, which: Int ->
            when (items[which]) {

                MuscleGroup.ABS.muscleName -> {
                    callback(addNewGroupOfMuscles(currentDay, items[which]))
                }
                MuscleGroup.CHEST.muscleName -> {
                    callback(addNewGroupOfMuscles(currentDay, items[which]))
                }
                MuscleGroup.TRICEPS.muscleName -> {
                    callback(addNewGroupOfMuscles(currentDay, items[which]))
                }
                MuscleGroup.LEGS.muscleName ->{
                    callback(addNewGroupOfMuscles(currentDay, items[which]))

                }
                MuscleGroup.BACK.muscleName ->{
                    callback(addNewGroupOfMuscles(currentDay, items[which]))
                }
                MuscleGroup.BICEPS.muscleName -> {
                    callback(addNewGroupOfMuscles(currentDay, items[which]))

                }
                MuscleGroup.SHOULDERS.muscleName -> {
                    callback(addNewGroupOfMuscles(currentDay, items[which]))
                }
            }
        }
        alertBuilder.create().show()
    }


    private  fun addNewGroupOfMuscles(currentDay: String, muscle: String): MuscleGroupModel {

        val muscleGroup = MuscleGroupModel(
            day = currentDay,
            muscleName = muscle
        )

        MuscleGroupRepo(context).insertNewMuscleGroup(muscleGroup)
        Log.d("Callback", "Muscles sent to database")

        return muscleGroup

    }


}

