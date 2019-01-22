package com.pinetreeapps.shinystationframe.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.pinetreeapps.shinystationframe.dagger.AppDelegate
import com.pinetreeapps.shinystationframe.data.repository.WeatherStationApi
import com.pinetreeapps.shinystationframe.page.alarm.util.AlarmHelper
import javax.inject.Inject

class BootCompletedReceiver : BroadcastReceiver() {

    init {
        AppDelegate.graph.inject(this)
    }

    @Inject
    lateinit var repository: WeatherStationApi

    @Inject
    lateinit var context: Context

    override fun onReceive(p0: Context?, p1: Intent?) {
        if(p1?.action != "android.intent.action.BOOT_COMPLETED") {
            return
        }

        val alarmList = repository.getAlarmList() ?: return
        AlarmHelper.rescheduleAlarmsAfterReboot(context, alarmList)

        //saving alarm list to trigger alarm status update
        repository.saveAlarmList(alarmList)
    }
}