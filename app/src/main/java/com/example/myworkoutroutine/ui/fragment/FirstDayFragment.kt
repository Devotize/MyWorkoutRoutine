package com.example.myworkoutroutine.ui.fragment

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.health.TimerStat
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myworkoutroutine.ui.items.MuscleItem
import com.example.myworkoutroutine.R
import com.example.myworkoutroutine.adapter.RecyclerMusclesAdapter
import com.example.myworkoutroutine.adapter.SwipeToDeleteCallback
import com.example.myworkoutroutine.database.entity.Exercise
import com.example.myworkoutroutine.database.entity.MuscleGroupModel
import com.example.myworkoutroutine.database.repo.MuscleGroupRepo
import com.example.myworkoutroutine.ui.MainActivity.Companion.currentDay
import com.example.myworkoutroutine.ui.items.ExerciseItem
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.snackbar.Snackbar
import com.xwray.groupie.*
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.exercise_item_layout.view.*
import kotlinx.android.synthetic.main.fragment_first_day.*
import kotlinx.android.synthetic.main.muscle_group_card.view.*
import java.util.*
import kotlin.collections.ArrayList


class FirstDayFragment : Fragment(){

    private lateinit var adapter: GroupAdapter<GroupieViewHolder>
    private lateinit var listOfExpandableGroup: MutableList<ExpandableGroup>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val view = inflater.inflate(R.layout.fragment_first_day, container, false)
        Log.d("FirstDayFragment", "View: view")
        adapter = GroupAdapter()

        val toolbar = activity?.findViewById<CollapsingToolbarLayout>(R.id.collapsing_toolbar)
        toolbar?.title = currentDay
        listOfExpandableGroup = returnListOfExpandableGroups().toMutableList()

        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            initRecyclerView()


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.dayly_fragment_menu, menu)

        val menuItem = menu.findItem(R.id.menu_add_new_muscle_group)
        menuItem.setOnMenuItemClickListener {
            RecyclerMusclesAdapter(
                requireContext()
            ).showAddMusclesDialog(currentDay){
                Log.d("Callback", "Callback given!")
//                activity?.findNavController(R.id.nav_host_fragment)?.navigate(R.id.action_firstDayFragment_to_firstDayFragment)
                listOfExpandableGroup = returnListOfExpandableGroups().toMutableList()
                adapter.add(listOfExpandableGroup.last())
            }

            true
        }
    }


    private fun initRecyclerView() {
        first_day_recycler_view.adapter = adapter
        val layoutManager = LinearLayoutManager(context)
        first_day_recycler_view.layoutManager = layoutManager
        var isCompleted = true

        adapter.addAll(listOfExpandableGroup)

        adapter.setOnItemClickListener { item, view ->
            Log.d("FirstDayFragment", "Clicked on item: $item, view: $view")
            val exerciseItem: ExerciseItem = item as ExerciseItem

        }

        val onSwipeToDeleteCallback = object : SwipeToDeleteCallback(requireContext()) {

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                if (direction == ItemTouchHelper.LEFT) {
                    deleteItem(viewHolder)
                }

                if (direction == ItemTouchHelper.RIGHT) {

//                    isCompleted = !isCompleted
                    if (isCompleted) {
                        viewHolder.itemView.muscle_group_frame_layout.setBackgroundColor(Color.GREEN)
                        Toast.makeText(context, "${viewHolder.itemView.muscle_group_text_view.text} comlepted", Toast.LENGTH_SHORT).show()
                    } else {
                        viewHolder.itemView.muscle_group_frame_layout.setBackgroundColor(Color.GREEN)
                    }
                    adapter.notifyItemChanged(viewHolder.adapterPosition)
                    adapter.notifyItemChanged(viewHolder.adapterPosition)
                    adapter.getItem(viewHolder.adapterPosition).notifyChanged()
                }
            }

        }



        val itemTouchHelper = ItemTouchHelper(onSwipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(first_day_recycler_view)


    }

    private fun deleteItem(viewHolder: RecyclerView.ViewHolder) {
        val position = viewHolder.adapterPosition
        val currentMuscle = getMusclesForTheDay()[position]
        val currentItem = listOfExpandableGroup[position]
        deleteMuscleItemFromDatabase(currentMuscle)
        adapter.removeGroupAtAdapterPosition(position)
        listOfExpandableGroup.removeAt(position)

        Snackbar.make(viewHolder.itemView, "${currentMuscle.muscleName} removed", Snackbar.LENGTH_LONG)
            .setAction("UNDO") {
                MuscleGroupRepo(requireContext()).insertNewMuscleGroup(currentMuscle)
                adapter.add(position, currentItem)
                listOfExpandableGroup.add(position, currentItem)
        }.show()

    }

    private fun deleteMuscleItemFromDatabase(muscleGroupModel: MuscleGroupModel) {
        MuscleGroupRepo(requireContext()).deleteMuscleGroup(muscleGroupModel)
    }

    private fun returnListOfExpandableGroups(): List<ExpandableGroup>{
        val muscleGroupList = getMusclesForTheDay()
        val groupList = ArrayList<ExpandableGroup>()

        muscleGroupList.forEach {
            val exerciseSection = Section()

            val exercisesList: List<Exercise> = MuscleGroupRepo(requireContext()).getExercisesByMuscle(it.muscleName)
            exercisesList.forEach {
                val image = fromByteArrayToDrawable(it.image)
                val exerciseItem = ExerciseItem(it.name, image, it.duration)
                Log.d("FirstDayFragment", "Exercise item view type: ${exerciseItem.viewType}")
                exerciseSection.add(exerciseItem)
            }

            ExpandableGroup(MuscleItem(requireContext(), it.muscleName), false).apply {
                add(exerciseSection)
                groupList.add(this)
            }
        }

        return groupList

    }

    private fun fromByteArrayToDrawable(byteArray: ByteArray): Drawable {
        return BitmapDrawable(BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size))
    }

    private fun getMusclesForTheDay(): List<MuscleGroupModel> {

        return MuscleGroupRepo(requireContext()).getMusclesByDay(currentDay)

    }

    fun vibratePhone(context: Context) {
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= 29) {
            vibrator.vibrate(VibrationEffect.createOneShot(50, 1))
        } else {
            vibrator.vibrate(200)
        }
    }

}