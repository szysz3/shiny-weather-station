package com.pinetreeapps.shinystationframe.page.alarm.list

import android.graphics.Canvas
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import com.pinetreeapps.shinystationframe.widget.AlarmItem

class RecyclerItemTouchHelper(dragDirs: Int, swipeDirs: Int,
                              private val listener: RecyclerItemTouchHelperListener) :
        ItemTouchHelper.SimpleCallback(dragDirs, swipeDirs) {

    interface RecyclerItemTouchHelperListener {
        fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int, position: Int?)
    }

    override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?,
                        target: RecyclerView.ViewHolder?): Boolean {
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
        listener.onSwiped(viewHolder, direction, viewHolder?.adapterPosition)
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        if (viewHolder == null) {
            return
        }

        val alarmItem = viewHolder.itemView as AlarmItem
        ItemTouchHelper.Callback.getDefaultUIUtil().onSelected(alarmItem.foregroundView)
    }

    override fun onChildDraw(c: Canvas?, recyclerView: RecyclerView?,
                             viewHolder: RecyclerView.ViewHolder?, dX: Float, dY: Float,
                             actionState: Int, isCurrentlyActive: Boolean) {
        if (viewHolder == null) {
            return
        }

        val alarmItem = viewHolder.itemView as AlarmItem
        ItemTouchHelper.Callback.getDefaultUIUtil()
                .onDrawOver(c,
                            recyclerView,
                            alarmItem.foregroundView,
                            dX,
                            dY,
                            actionState,
                            isCurrentlyActive)
    }

    override fun clearView(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?) {
        if (viewHolder == null) {
            return
        }

        val alarmItem = viewHolder.itemView as AlarmItem
        ItemTouchHelper.Callback.getDefaultUIUtil().clearView(alarmItem.foregroundView)
    }

    override fun onChildDrawOver(c: Canvas?, recyclerView: RecyclerView?,
                                 viewHolder: RecyclerView.ViewHolder?, dX: Float, dY: Float,
                                 actionState: Int, isCurrentlyActive: Boolean) {
        if (viewHolder == null) {
            return
        }

        val alarmItem = viewHolder.itemView as AlarmItem
        ItemTouchHelper.Callback.getDefaultUIUtil()
                .onDraw(c,
                        recyclerView,
                        alarmItem.foregroundView,
                        dX,
                        dY,
                        actionState,
                        isCurrentlyActive)
    }
}