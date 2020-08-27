package com.example.myworkoutroutine.navigation

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.DisplayMetrics
import android.view.View
import android.view.animation.Interpolator
import android.widget.ImageView
import android.widget.Toast
import com.example.myworkoutroutine.R
import kotlinx.android.synthetic.main.activity_main.view.*

class NavigationIconClickListener @JvmOverloads internal constructor(
    private val context: Context, private val sheet: View, private val interpolator: Interpolator? = null,
    private val openIcon: Drawable? = null, private val closeIcon: Drawable? = null) : View.OnClickListener {

    private val animatorSet = AnimatorSet()
    private val height: Int
    private var backdropShown = false
    private var currentView: View? = null

    init {
        val displayMetrics = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
        height = displayMetrics.heightPixels

    }

    override fun onClick(view: View) {
        backdropShown = !backdropShown
        currentView = view
        // Cancel the existing animations
//        animatorSet.removeAllListeners()
//        animatorSet.end()
//        animatorSet.cancel()
//
//        updateIcon(view)
//
//        val translateY = height - (height - context.resources.getDimensionPixelSize(R.dimen.navigation_grid_reveal_height))
//
//        val animator = ObjectAnimator.ofFloat(sheet, "translationY", (if (backdropShown) translateY else 0).toFloat())
//        animator.duration = 500
//        if (interpolator != null) {
//            animator.interpolator = interpolator
//        }
//        animatorSet.play(animator)
//        animator.start()

        animateBackdrop(view)

    }

    private fun updateIcon(view: View) {
        if (openIcon != null && closeIcon != null) {
            if (view !is ImageView) {
                throw IllegalArgumentException("updateIcon() must be called on an ImageView")
            }
            if (backdropShown) {
                view.setImageDrawable(closeIcon)
            } else {
                view.setImageDrawable(openIcon)
            }
        }
    }

    private fun animateBackdrop(view: View) {

        // Cancel the existing animations
        animatorSet.removeAllListeners()
        animatorSet.end()
        animatorSet.cancel()

        updateIcon(view)

        val translateY = height - (height - context.resources.getDimensionPixelSize(R.dimen.navigation_grid_reveal_height))

        val animator = ObjectAnimator.ofFloat(sheet, "translationY", (if (backdropShown) translateY else 0).toFloat())
        animator.duration = 500
        if (interpolator != null) {
            animator.interpolator = interpolator
        }
        animatorSet.play(animator)
        animator.start()
    }

    fun closeBackdrop() {
        backdropShown = false
        currentView?.let { animateBackdrop(it) }
    }

}