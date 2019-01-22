package com.pinetreeapps.shinystationframe.page.alarm.util

import android.app.AlarmManager
import android.content.Context
import com.pinetreeapps.shinystationframe.data.model.AlarmModel
import org.joda.time.DateTime
import org.joda.time.DateTimeConstants
import org.joda.time.Days
import java.util.*
import kotlin.random.Random

object AlarmHelper {

    private val random = Random(System.currentTimeMillis())

    fun rescheduleAlarm(context: Context, alarmList: MutableList<AlarmModel>?, alarmId: String,
                        alarmOffsetId: String) {
        if (alarmList == null) {
            return
        }

        var alarm: AlarmModel? = null
        for (alarmElement in alarmList) {
            if (alarmElement.id == alarmId) {
                alarm = alarmElement
            }
        }

        if (alarm == null) {
            return
        }

        var alarmOffsetPair: Map.Entry<String, Int>? = null
        for (mapPair in alarm.dayOffsets) {
            if (mapPair.key == alarmOffsetId) {
                alarmOffsetPair = mapPair
            }
        }

        if (alarmOffsetPair == null) {
            return
        }

        alarm.alarmSetDateTimestampMillis = DateTime(Calendar.getInstance().timeInMillis).millis

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        setAlarm(context, alarmManager, alarm, alarmOffsetPair.key, Days.SEVEN.days)
    }

    fun rescheduleAlarmsAfterReboot(context: Context, alarmList: MutableList<AlarmModel>) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmList.forEach {alarmModel ->
            if(alarmModel.isEnabled) {
                alarmModel.dayOffsets.forEach {dayOffset ->
                    val alarmTime = DateTime(alarmModel.alarmSetDateTimestampMillis).withTime(alarmModel.alarmHour,
                            alarmModel.alarmMinute,
                            0,
                            0)
                            .plusDays(dayOffset.value)
                    if(alarmTime < DateTime()) {
                        rescheduleAlarm(context, alarmList, alarmModel.id, dayOffset.key)
                    } else {
                        setAlarm(context, alarmManager, alarmModel, dayOffset.key, dayOffset.value)
                    }
                }
            }
        }
    }

    fun createAndScheduleAlarm(context: Context, hour: Int, minute: Int, days: List<Int>,
                               alarmList: MutableList<AlarmModel>): MutableList<AlarmModel> {
        val alarm = getAlarm(hour, minute, days)
        alarmList.add(alarm)

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarm.dayOffsets.forEach {
            setAlarm(context, alarmManager, alarm, it.key, it.value)
        }

        return alarmList
    }

    fun isAlarmScheduled(context: Context, alarm: AlarmModel): Boolean {
        for(alarmOffsetKey in alarm.dayOffsets) {
            val alarmSetDate = DateTime(alarm.alarmSetDateTimestampMillis).withTime(alarm.alarmHour,
                    alarm.alarmMinute,
                    0,
                    0).plusDays(alarmOffsetKey.value)
            if (AlarmIntents.getCheckIfAlarmScheduled(context, alarm.pendingAlarmId,
                            alarmSetDate.millis, alarm.id, alarmOffsetKey.key) != null) {
                return true
            }
        }

        return false
    }

    private fun setAlarm(context: Context, alarmManager: AlarmManager, alarm: AlarmModel,
                         alarmOffsetId: String, alarmOffset: Int) {
        val alarmSetDate = DateTime(alarm.alarmSetDateTimestampMillis).withTime(alarm.alarmHour,
                                                                                alarm.alarmMinute,
                                                                                0,
                                                                                0)
                .plusDays(alarmOffset)

        val alarmPendingIntent = AlarmIntents.getFireAlarmPendingIntent(context,
                                                                        alarm.pendingAlarmId,
                                                                        alarmSetDate.millis,
                                                                        alarm.id,
                                                                        alarmOffsetId)
        alarmManager.setAlarmClock(AlarmManager.AlarmClockInfo(alarmSetDate.millis,
                                                               alarmPendingIntent),
                                   alarmPendingIntent)
    }

    private fun getAlarm(hour: Int, minute: Int, days: List<Int>): AlarmModel {
        val dau = DateTime(Calendar.getInstance().timeInMillis)

        val da = dau.dayOfWeek
        val offsets = mutableMapOf<String, Int>()
        days.forEach {
            var offset = 0
            if (da > it) {
                offset = DateTimeConstants.DAYS_PER_WEEK - (da - it)
            } else {
                offset = it - da
                if (offset == 0) {
                    if (dau.hourOfDay().get() > hour && dau.minuteOfHour().get() >= minute) {
                        offset = DateTimeConstants.DAYS_PER_WEEK
                    }
                }
            }

            offsets[UUID.randomUUID().toString()] = offset
        }

        return AlarmModel(dau.millis,
                          random.nextInt(Int.MAX_VALUE),
                          hour,
                          minute,
                          UUID.randomUUID().toString(),
                          offsets,
                          true,
                          days,
                          getDaysRepresentation(days))
    }

    private fun getDaysRepresentation(days: List<Int>): String {
        var daysRepresentation = ""
        days.take(1).forEach {
            daysRepresentation += mapToDay(it)
        }
        days.takeLast(days.size - 1).forEach {
            daysRepresentation += ", ${mapToDay(it)}"
        }

        return daysRepresentation
    }

    private fun mapToDay(intDay: Int): String {
        when (intDay) {
            1 -> return "mon"
            2 -> return "tue"
            3 -> return "wed"
            4 -> return "thu"
            5 -> return "fri"
            6 -> return "sat"
            7 -> return "sun"
        }

        return ""
    }
}