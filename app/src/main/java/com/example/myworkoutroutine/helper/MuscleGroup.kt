package com.example.myworkoutroutine.helperЕ

import android.graphics.drawable.Drawable
import androidx.annotation.StringRes
import com.example.myworkoutroutine.R
import com.example.myworkoutroutine.ui.MainActivity

enum class MuscleGroup(val muscleName: String, val image: Drawable) {
    ABS(MainActivity.context.getString(R.string.abs),
    MainActivity.context.getDrawable(R.drawable.abs_icon)!!),
    CHEST(MainActivity.context.getString(R.string.chest),
    MainActivity.context.getDrawable(R.drawable.chest_icon)!!),
    TRICEPS(MainActivity.context.getString(R.string.triceps),
    MainActivity.context.getDrawable(R.drawable.triceps_icon)!!),
    LEGS(MainActivity.context.getString(R.string.legs),
    MainActivity.context.getDrawable(R.drawable.legs_icon)!!),
    BACK(MainActivity.context.getString(R.string.back),
    MainActivity.context.getDrawable(R.drawable.back_muscles_icon)!!),
    BICEPS(MainActivity.context.getString(R.string.biceps),
    MainActivity.context.getDrawable(R.drawable.biceps_icon)!!),
    SHOULDERS(MainActivity.context.getString(R.string.shoulders),
    MainActivity.context.getDrawable(R.drawable.shoulders_icon)!!);

}