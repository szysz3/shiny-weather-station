package com.pinetreeapps.shinystationframe.data.model

data class AlarmModel(var alarmSetDateTimestampMillis: Long, val pendingAlarmId: Int, val alarmHour: Int,
                      val alarmMinute: Int, val id: String, val dayOffsets: Map<String, Int>,
                      var isEnabled: Boolean, val days: List<Int>, val daysRepresentation: String)
