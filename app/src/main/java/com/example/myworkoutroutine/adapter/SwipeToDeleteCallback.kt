package com.example.myworkoutroutine.adapter

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.myworkoutroutine.R
import com.example.myworkoutroutine.ui.fragment.FirstDayFragment
import com.example.myworkoutroutine.ui.items.MuscleItem
import com.xwray.groupie.TouchCallback
import kotlinx.android.synthetic.main.muscle_group_card.view.*

abstract class SwipeToDeleteCallback(val context: Context): TouchCallback() {

    private var halfWaySwipedLeft = true

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val swapFlag = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        val dragFlag = ItemTouchHelper.DOWN or ItemTouchHelper.UP
        Log.d("SwipeToDeleteCallback","Item view type: ${viewHolder.itemViewType}")
        return makeMovementFlags(dragFlag, swapFlag)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val itemView = viewHolder.itemView
        val itemHeight = itemView.height
        val itemWidth = itemView.width

        if (dX < 0) {
            val drawable: Drawable = context.getDrawable(R.drawable.ic_baseline_delete_24)!!
            drawable.setBounds(
                itemView.right - (itemWidth / 5),
                itemView.top + (itemHeight / 4),
                itemView.right - (itemWidth / 11),
                itemView.bottom - itemHeight / 4)
            val bitmap: Bitmap = Bitmap.createBitmap(itemView.width, itemView.height, Bitmap.Config.ARGB_8888)
            val redPaint: Paint = Paint()
            redPaint.color = context.resources.getColor(R.color.colorDelete)

            c.drawBitmap(bitmap, 1.0f, 1.0f, null)
            if (dX <= -(itemWidth / 2)) {
                c.clipRect(itemView.left, itemView.top, itemView.right, itemView.bottom)
                c.drawPaint(redPaint)
                if (halfWaySwipedLeft) {
                    FirstDayFragment().vibratePhone(context)
                    halfWaySwipedLeft = false
                }
            }
            else {
                halfWaySwipedLeft = true
            }

            when (actionState) {
                ItemTouchHelper.ACTION_STATE_IDLE -> {
                    c.drawColor(context.resources.getColor(R.color.colorPrimaryDark))
                }
                ItemTouchHelper.ACTION_STATE_SWIPE -> {
                    drawable.draw(c)
                }
            }
        }

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }


}