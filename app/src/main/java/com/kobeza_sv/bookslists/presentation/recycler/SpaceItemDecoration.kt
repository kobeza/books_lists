package com.kobeza_sv.bookslists.presentation.recycler

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpaceItemDecoration(
    private val edgeOffset: Int,
    private val middleOffset: Int = edgeOffset,
    @RecyclerView.Orientation private val orientation: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val isFirstItem = position == 0
        val isLastItem = position == parent.adapter?.itemCount?.minus(1) ?: -1

        if (orientation == RecyclerView.HORIZONTAL) {
            when {
                isFirstItem -> outRect.left = edgeOffset
                isLastItem -> {
                    outRect.left = middleOffset
                    outRect.right = edgeOffset
                }
                else -> outRect.left = middleOffset
            }
        } else {
            when {
                isFirstItem -> outRect.top = edgeOffset
                isLastItem -> {
                    outRect.top = middleOffset
                    outRect.bottom = edgeOffset
                }
                else -> outRect.top = middleOffset
            }
        }
    }
}