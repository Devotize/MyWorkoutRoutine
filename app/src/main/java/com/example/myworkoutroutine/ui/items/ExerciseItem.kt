package com.example.myworkoutroutine.ui.items

import android.graphics.drawable.Drawable
import android.util.Log
import com.example.myworkoutroutine.R
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.exercise_item_layout.view.*

class ExerciseItem(
    private val name: String = "",
    private val image: Drawable? = null,
    private val duration: Int = 0
): Item() {

    override fun getViewType(): Int {
        return super.getViewType()
    }

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        val itemView = viewHolder.itemView
        itemView.exercise_image.setImageDrawable(image)
        itemView.exercise_name.text = name
        itemView.exercise_duration.text = duration.toString()
        Log.d("SwipeToDeleteCallback","Item view type in exercise item: ${viewHolder.itemViewType}")
    }

    override fun getLayout(): Int {
        return R.layout.exercise_item_layout
    }

}