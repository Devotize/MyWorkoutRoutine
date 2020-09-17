package com.example.myworkoutroutine.ui.items

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myworkoutroutine.R
import com.example.myworkoutroutine.database.entity.Exercise
import com.example.myworkoutroutine.helper.RecyclerItemDecorationSheet
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.recycler_view_exercise_section_layout.view.*

class RecyclerItem(exercises: List<Exercise>, context: Context): Item() {

    private val myContext = context
    private val myExercises = exercises

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        Log.d("RecyclerItem", "Bind")
        val itemView = viewHolder.itemView
        val adapter = GroupAdapter<GroupieViewHolder>()
        itemView.recycler_view_exercise_section.adapter = adapter
        itemView.recycler_view_exercise_section.layoutManager = LinearLayoutManager(myContext, LinearLayoutManager.HORIZONTAL, false)
        if (itemView.recycler_view_exercise_section.itemDecorationCount == 0) {
            itemView.recycler_view_exercise_section.addItemDecoration(RecyclerItemDecorationSheet(15,0,15,0))
        }
        Log.d("RecyclerItem", "Decoration count: ${itemView.recycler_view_exercise_section.itemDecorationCount}")
        myExercises.forEach {
            val image = BitmapDrawable(BitmapFactory.decodeByteArray(it.image, 0, it.image.size))
            adapter.add(ExerciseItem(it, myExercises))
        }
    }

    override fun getLayout(): Int {
        return R.layout.recycler_view_exercise_section_layout
    }

}