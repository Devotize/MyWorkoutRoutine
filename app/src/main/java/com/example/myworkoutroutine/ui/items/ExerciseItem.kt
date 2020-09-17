package com.example.myworkoutroutine.ui.items

import android.app.Activity
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.example.myworkoutroutine.R
import com.example.myworkoutroutine.database.entity.Exercise
import com.example.myworkoutroutine.ui.ExerciseDialogFragment
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.exercise_item_layout.view.*

class ExerciseItem(
    private val exercise: Exercise,
    private val exercises: List<Exercise>
): Item() {

    companion object{
        const val KEY = "EXERCISE_ITEM_KEY"
    }

    val image = BitmapDrawable(BitmapFactory.decodeByteArray(exercise.image, 0, exercise.image.size))
    val name = exercise.name
    private val duration = exercise.duration

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        val itemView = viewHolder.itemView

        itemView.exercise_image.setImageDrawable(image)
        itemView.exercise_name.text = name
        itemView.exercise_duration.text = duration.toString()
        Log.d("SwipeToDeleteCallback","Item view type in exercise item: ${viewHolder.itemViewType}")

        itemView.setOnClickListener {
            startDialogFragment(it.context)
        }

    }

    override fun getLayout(): Int {
        return R.layout.exercise_item_layout
    }

    private fun startDialogFragment(context: Context) {
//        val intent = Intent(context, ExerciseActivity::class.java)
        val bundle = Bundle()
        bundle.putSerializable(KEY, exercise)
//        intent.putExtras(bundle)
//        ActivityCompat.startActivity(context, intent, null)
        val fragment = ExerciseDialogFragment(exercises)
        fragment.arguments = bundle
        val fragmentManager = (context as FragmentActivity).supportFragmentManager
        fragment.show(fragmentManager, "ExerciseDialogFragment")
    }


}