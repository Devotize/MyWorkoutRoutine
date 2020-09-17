package com.example.myworkoutroutine.helper

import android.graphics.Rect
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RecyclerItemDecorationSheet(left: Int, top: Int, right: Int, bottom: Int): RecyclerView.ItemDecoration() {

    private val marginLeft = left
    private val marginRight = right
    private val marginTop = top
    private val marginBot = bottom

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
//        Log.d("RecyclerItemDecorationSheet", "Recycler view state: $state")
        outRect.setEmpty()
        outRect.set(marginLeft,marginTop,marginRight,marginBot)
        Log.d("RecyclerItemDecorationSheet", "outRect: $outRect")

    }

}