package com.example.filmsdb

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class ItemDecoration(
    context: Context,
    private val paddingLeft: Int,
    private val paddingRight: Int
) : RecyclerView.ItemDecoration() {

    private var divider: Drawable? = null

    init {
        divider = ContextCompat.getDrawable(context, R.drawable.divider_custom)
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)

        val left = parent.paddingLeft + paddingLeft
        val right = parent.width - parent.paddingRight - paddingRight

        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin
            val bottom = top + (divider?.intrinsicHeight ?: 0)

            divider?.let {
                it.setBounds(left, top, right, bottom)
                it.draw(c)
            }

        }
    }
}