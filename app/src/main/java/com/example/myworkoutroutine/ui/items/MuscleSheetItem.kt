package com.example.myworkoutroutine.ui.items

import android.content.Context
import com.example.myworkoutroutine.R
import com.example.myworkoutroutine.database.entity.MuscleGroupModel
import com.example.myworkoutroutine.database.repo.MuscleGroupRepo
import com.example.myworkoutroutine.helperЕ.MuscleGroup
import com.example.myworkoutroutine.ui.MainActivity.Companion.context
import com.example.myworkoutroutine.ui.MainActivity.Companion.currentDay
import com.example.myworkoutroutine.ui.fragment.FirstDayFragment
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.muscle_sheet_card.view.*

class MuscleSheetItem(muscle: MuscleGroup): Item() {

    val currentMuscle = muscle

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        val itemView = viewHolder.itemView
        itemView.muscle_sheet_card_image_view.setImageDrawable(currentMuscle.image)
        itemView.muscle_sheet_name_text_view.text = currentMuscle.muscleName

    }

    override fun getLayout(): Int {
        return R.layout.muscle_sheet_card
    }

}