package com.example.myworkoutroutine.ui.items

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.myworkoutroutine.R
import com.example.myworkoutroutine.helperЕ.MuscleGroup
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.ExpandableItem
import com.xwray.groupie.TouchCallback
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.muscle_group_card.view.*

class MuscleItem(context: Context, private val muscle: String = ""): Item(), ExpandableItem {

    private lateinit var expandableGroup: ExpandableGroup

    private val image = when (muscle) {
        MuscleGroup.ABS.muscleName -> MuscleGroup.ABS.image
        MuscleGroup.CHEST.muscleName -> MuscleGroup.CHEST.image
        MuscleGroup.TRICEPS.muscleName -> MuscleGroup.TRICEPS.image
        MuscleGroup.LEGS.muscleName -> MuscleGroup.LEGS.image
        MuscleGroup.BACK.muscleName -> MuscleGroup.BACK.image
        MuscleGroup.BICEPS.muscleName -> MuscleGroup.BICEPS.image
        MuscleGroup.SHOULDERS.muscleName-> MuscleGroup.SHOULDERS.image
        else -> context.getDrawable(R.drawable.ic_launcher_background)
    }

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        val itemView = viewHolder.itemView
        itemView.muscle_group_text_view.text = muscle
        itemView.muscle_group_image_view.setImageDrawable(image)
        itemView.expand_exercises_button.setBackgroundResource(getArrowImage())

        itemView.expand_exercises_button.setOnClickListener {
            expandableGroup.onToggleExpanded()
            itemView.expand_exercises_button.setBackgroundResource(getArrowImage())
        }

    }

    override fun getLayout(): Int {
        return R.layout.muscle_group_card
    }

    override fun setExpandableGroup(onToggleListener: ExpandableGroup) {
        expandableGroup = onToggleListener
    }

    @DrawableRes
    private fun getArrowImage(): Int {
        return if (expandableGroup.isExpanded) {
            R.drawable.ic_baseline_keyboard_arrow_up_24
        } else {
            R.drawable.ic_baseline_keyboard_arrow_down_24
        }
    }

    override fun isClickable(): Boolean {
        return false
    }

}