package com.example.myworkoutroutine.ui.items

import android.graphics.drawable.Drawable
import com.example.myworkoutroutine.R
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.exercise_item_layout.view.*

class ExerciseItem(
    private val name: String,
    private val image: Drawable,
    private val duration: Int
): Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        val itemView = viewHolder.itemView
        itemView.exercise_image.setImageDrawable(image)
        itemView.exercise_name.text = name
        itemView.exercise_duration.text = duration.toString()
    }

    override fun getLayout(): Int {
        return R.layout.exercise_item_layout
    }
}