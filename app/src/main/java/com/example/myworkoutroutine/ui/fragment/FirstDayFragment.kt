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
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myworkoutroutine.ui.items.MuscleItem
import com.example.myworkoutroutine.R
import com.example.myworkoutroutine.adapter.SwipeToDeleteCallback
import com.example.myworkoutroutine.database.entity.Exercise
import com.example.myworkoutroutine.database.entity.MuscleGroupModel
import com.example.myworkoutroutine.database.repo.MuscleGroupRepo
import com.example.myworkoutroutine.ui.MainActivity.Companion.currentDay
import com.example.myworkoutroutine.ui.items.ExerciseItem
import com.example.myworkoutroutine.ui.items.RecyclerItem
import com.example.myworkoutroutine.ui.sheet.BottomAddMuscleSheet
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.snackbar.Snackbar
import com.xwray.groupie.*
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_first_day.*
import kotlinx.android.synthetic.main.muscle_group_card.view.*
import kotlin.collections.ArrayList


class FirstDayFragment : Fragment(){

    lateinit var adapter: GroupAdapter<GroupieViewHolder>
    lateinit var listOfExpandableGroup: MutableList<ExpandableGroup>

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

    override fun onPause() {
        super.onPause()
        Log.d("FirstDayFragment", "On Pause")
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.dayly_fragment_menu, menu)

        val menuItem = menu.findItem(R.id.menu_add_new_muscle_group)
        menuItem.setOnMenuItemClickListener {
//            RecyclerMusclesAdapter(
//                requireContext()
//            ).showAddMusclesDialog(currentDay){
//                Log.d("Callback", "Callback given!")
////                activity?.findNavController(R.id.nav_host_fragment)?.navigate(R.id.action_firstDayFragment_to_firstDayFragment)
//                listOfExpandableGroup = returnListOfExpandableGroups().toMutableList()
//                adapter.add(listOfExpandableGroup.last())
//            }

            val myBottomSheet = BottomAddMuscleSheet()

            activity?.supportFragmentManager?.let { it1 -> myBottomSheet.show(it1, BottomAddMuscleSheet.TAG) }
            myBottomSheet.onItemAdded {
                listOfExpandableGroup = returnListOfExpandableGroups().toMutableList()
                adapter.add(listOfExpandableGroup.last())
                myBottomSheet.dismiss()
            }
            true
        }
    }


    private fun initRecyclerView() {
        first_day_recycler_view.adapter = adapter
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        first_day_recycler_view.layoutManager = layoutManager

        adapter.addAll(listOfExpandableGroup)

        adapter.setOnItemClickListener { item, view ->
            Log.d("FirstDayFragment", "Clicked on item: $item, view: $view")
            val exerciseItem: ExerciseItem = item as ExerciseItem

        }

        val onSwipeToDeleteCallback = object : SwipeToDeleteCallback(requireContext()) {

            var isCompleted = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                if (direction == ItemTouchHelper.LEFT) {
                    deleteItem(viewHolder)
                }

                if (direction == ItemTouchHelper.RIGHT) {


                    isCompleted = !isCompleted
                    if (isCompleted) {
                        Toast.makeText(context, "Is completed: $isCompleted (completed)", Toast.LENGTH_SHORT).show()
                        val v = viewHolder.itemView
                        v.muscle_group_frame_layout.setBackgroundColor(Color.GREEN)
                        v.refreshDrawableState()
                        v.muscle_group_frame_layout.invalidate()
                        v.muscle_group_frame_layout.refreshDrawableState()
                        v.invalidate()
                    } else {
                        Toast.makeText(context, "Is completed: $isCompleted (uncompleted)", Toast.LENGTH_SHORT).show()
                        val v = viewHolder.itemView
                        v.muscle_group_frame_layout.setBackgroundColor(Color.BLACK)
                        v.refreshDrawableState()
                        v.muscle_group_frame_layout.invalidate()
                        v.muscle_group_frame_layout.refreshDrawableState()
                        v.invalidate()
                    }
                    adapter.notifyItemChanged(viewHolder.adapterPosition)

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
            exerciseSection.add(RecyclerItem(exercisesList, requireContext()))
//            exercisesList.forEach {
//                val image = fromByteArrayToDrawable(it.image)
//                val exerciseItem = ExerciseItem(it.name, image, it.duration)
//                Log.d("FirstDayFragment", "Exercise item view type: ${exerciseItem.viewType}")
//                exerciseSection.add(exerciseItem)
//            }

            ExpandableGroup(MuscleItem(requireContext(), it.muscleName), false).apply {
                add(exerciseSection)
                groupList.add(this)
            }
        }

        return groupList

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