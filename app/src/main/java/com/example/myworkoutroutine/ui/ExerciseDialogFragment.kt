package com.example.myworkoutroutine.ui

import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.myworkoutroutine.R
import com.example.myworkoutroutine.database.entity.Exercise
import com.example.myworkoutroutine.ui.items.ExerciseItem
import kotlinx.android.synthetic.main.exercise_dialog_fragment_layout.*

class ExerciseDialogFragment(private val exercises: List<Exercise>) : DialogFragment() {

    private lateinit var exercise: Exercise
    private var countDownTimer: CountDownTimer? = null
    private var currentExerciseIndex: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.exercise_dialog_fragment_layout, container, false)
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        exercise = arguments?.getSerializable(ExerciseItem.KEY) as Exercise
        currentExerciseIndex = exercises.indexOf(exercise)

        loadViews()
        initCloseButton()
    }

    override fun onStart() {
        super.onStart()
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            getDialog()?.window?.setLayout(width, height)
            getDialog()?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    private fun initCloseButton() {
        close_dialog_button.setOnClickListener {
            this@ExerciseDialogFragment.dismiss()
        }
    }

    override fun dismiss() {
        countDownTimer?.cancel()
        super.dismiss()
    }

    private fun startTimer(exerciseDuration: Int) {

        val time: Long = exerciseDuration.toLong() * 1000

        countDownTimer = object : CountDownTimer(time, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                val timeInSec = millisUntilFinished / 1000
                dialog_exercise_timer.text = if (timeInSec < 60) "00:${timeInSec}" else "${timeInSec/60}:${timeInSec%1*60}"
            }

            override fun onFinish() {
                if (currentExerciseIndex == exercises.size - 1) {
                    this@ExerciseDialogFragment.dismiss()
                }
                currentExerciseIndex++
                exercise = exercises[currentExerciseIndex]
                loadViews()
            }
        }.start()
    }

    private fun loadViews() {
        dialog_exercise_image.background = BitmapDrawable(context?.resources, BitmapFactory.decodeByteArray(exercise.image, 0, exercise.image.size))

        if (exercise.duration <= 10) {
            initNumberButton()
        } else {
            startTimer(exercise.duration)
        }
    }

    private fun initNumberButton() {
        var numberOfRepeats = exercise.duration
        number_of_repeat_button.apply {
            text = numberOfRepeats.toString()
            visibility = View.VISIBLE
            setOnClickListener {
                if (numberOfRepeats == 0) {
                    this@ExerciseDialogFragment.dismiss()
                }
                numberOfRepeats--
            }
        }

    }

}















