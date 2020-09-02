package com.example.myworkoutroutine.ui.sheet

import android.icu.lang.UCharacter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myworkoutroutine.R
import com.example.myworkoutroutine.database.entity.MuscleGroupModel
import com.example.myworkoutroutine.database.repo.MuscleGroupRepo
import com.example.myworkoutroutine.helper.RecyclerItemDecorationSheet
import com.example.myworkoutroutine.helperЕ.MuscleGroup
import com.example.myworkoutroutine.ui.MainActivity
import com.example.myworkoutroutine.ui.fragment.FirstDayFragment
import com.example.myworkoutroutine.ui.items.MuscleItem
import com.example.myworkoutroutine.ui.items.MuscleSheetItem
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.bottom_add_muscle_sheet.*

class BottomAddMuscleSheet() : BottomSheetDialogFragment() {

    private var adapter = GroupAdapter<GroupieViewHolder>()

    companion object {
        const val TAG = "BottomAddMuscleSheet"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bottom_add_muscle_sheet, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecyclerView{
//            FirstDayFragment.adapter
//            FirstDayFragment().listOfExpandableGroup
        }
    }

    private fun initRecyclerView(callback: (MuscleGroupModel?) -> Unit) {
        sheet_recycler_view.adapter = adapter
        sheet_recycler_view.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        sheet_recycler_view.addItemDecoration(RecyclerItemDecorationSheet(12,0,12,20))

        val groupList = ArrayList<MuscleSheetItem>()

        MuscleGroup.values().forEach {
            groupList.add(MuscleSheetItem(it))
        }
        adapter.addAll(groupList)


    }

    fun onItemAdded(callback: (MuscleGroupModel) -> Unit) {
        adapter.setOnItemClickListener { item, view ->
            val muscle: MuscleSheetItem = item as MuscleSheetItem
            muscle.currentMuscle

            val muscleModel = MuscleGroupModel(
                muscleName = muscle.currentMuscle.muscleName,
                day = MainActivity.currentDay
            )
            MuscleGroupRepo(MainActivity.context).insertNewMuscleGroup(muscleModel)

            callback(muscleModel)

        }
    }

}