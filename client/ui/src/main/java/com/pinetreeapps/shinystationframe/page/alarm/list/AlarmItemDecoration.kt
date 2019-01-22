package com.pinetreeapps.shinystationframe.page.alarm.list

import android.content.Context
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View
import com.pinetreeapps.shinystationframe.R

class AlarmItemDecoration(context: Context) : RecyclerView.ItemDecoration() {

    private val verticalSpaceHeight: Int =
            context.resources.getDimensionPixelSize(R.dimen.alarm_item_margin)

    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?,
                                state: RecyclerView.State?) {
        super.getItemOffsets(outRect, view, parent, state)

        if (parent != null && parent.adapter != null) {
            if (parent.getChildAdapterPosition(view) != parent.adapter.itemCount - 1) {
                outRect?.bottom = verticalSpaceHeight
            }
        }
    }
}