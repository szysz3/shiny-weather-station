package com.pinetreeapps.shinystationframe.page.alarm.util

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.pinetreeapps.shinystationframe.page.alarm.fired.FiredAlarmActivity

object AlarmIntents {
    const val ALARM_TIMESTAMP_KEY = "timestamp"
    const val ALARM_ID_KEY = "alarmId"
    const val ALARM_OFFSET_ID_KEY = "alarmOffsetId"

    private fun getFireAlarmIntent(context: Context, alarmTimestamp: Long, alarmId: String,
                                   alarmOffsetId: String): Intent {
        val intent = Intent(context, FiredAlarmActivity::class.java)

        val extras = Bundle()
        extras.putLong(ALARM_TIMESTAMP_KEY, alarmTimestamp)
        extras.putString(ALARM_ID_KEY, alarmId)
        extras.putString(ALARM_OFFSET_ID_KEY, alarmOffsetId)

        intent.putExtras(extras)
        return intent
    }

    fun getFireAlarmPendingIntent(context: Context, pendingAlarmId: Int, alarmTimestamp: Long, alarmId: String,
                                  alarmOffsetId: String): PendingIntent {
        val intent = getFireAlarmIntent(context, alarmTimestamp, alarmId, alarmOffsetId)
        return PendingIntent.getActivity(context,
                                         pendingAlarmId,
                                         intent,
                                         PendingIntent.FLAG_UPDATE_CURRENT)
    }

    fun getCheckIfAlarmScheduled(context: Context, pendingAlarmId: Int, alarmTimestamp: Long, alarmId: String,
                                 alarmOffsetId: String) : PendingIntent? {
        val intent = getFireAlarmIntent(context, alarmTimestamp, alarmId, alarmOffsetId)
        return PendingIntent.getActivity(context, pendingAlarmId, intent, PendingIntent.FLAG_NO_CREATE)
    }
}