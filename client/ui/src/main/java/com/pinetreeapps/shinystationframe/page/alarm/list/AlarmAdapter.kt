package com.pinetreeapps.shinystationframe.page.alarm.list

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.pinetreeapps.shinystationframe.data.model.AlarmModel
import com.pinetreeapps.shinystationframe.util.ValueFormatter
import com.pinetreeapps.shinystationframe.widget.AlarmItem

class AlarmAdapter(private var alarmList: MutableList<AlarmModel>) :
        RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder>() {

    interface AlarmRemovedListener {
        fun onAlarmRemoved(alarmList: MutableList<AlarmModel>)
    }

    var alarmRemovedListener: AlarmRemovedListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder {
        return AlarmViewHolder(AlarmItem(parent.context))
    }

    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
        holder.unbind()
        holder.bind(alarmList[position])
    }

    override fun getItemCount(): Int {
        return alarmList.size
    }

    fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int, position: Int?) {
        if (position == null) {
            return
        }

        alarmList.removeAt(position)
        notifyItemRemoved(position)
        alarmRemovedListener?.onAlarmRemoved(alarmList)
    }

    class AlarmViewHolder(alarmView: AlarmItem) : RecyclerView.ViewHolder(alarmView) {
        fun bind(alarmModel: AlarmModel) {
            val alarmItem = itemView as AlarmItem
            alarmItem.alarmText =
                    "${ValueFormatter.formatIntWithLeadingZero(alarmModel.alarmHour)}:${ValueFormatter.formatIntWithLeadingZero(
                            alarmModel.alarmMinute)}"
            alarmItem.alarmRepeatText = alarmModel.daysRepresentation
            alarmItem.alarmEnabled = alarmModel.isEnabled
            alarmItem.alarmItemChangedListener = object : AlarmItem.AlarmItemChangedListener {
                override fun onAlarmItemEnabled(isEnabled: Boolean) {
                    alarmModel.isEnabled = isEnabled
                }
            }
        }

        fun unbind() {
            val alarmItem = itemView as AlarmItem
            alarmItem.alarmText = ""
            alarmItem.alarmRepeatText = ""
            alarmItem.alarmItemChangedListener = null
            alarmItem.alarmEnabled = false
        }
    }
}