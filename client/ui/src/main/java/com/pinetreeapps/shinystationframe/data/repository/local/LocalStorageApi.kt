package com.pinetreeapps.shinystationframe.data.repository.local

import android.content.SharedPreferences
import com.pinetreeapps.shinystationframe.data.model.AlarmModel

interface LocalStorageApi {
    fun getAlarmList(): MutableList<AlarmModel>?
    fun saveAlarmList(alarmList: MutableList<AlarmModel>)
    fun registerLocalDataChangedListener(
            listener: SharedPreferences.OnSharedPreferenceChangeListener)

    fun unregisterLocalDataChangedListener(
            listener: SharedPreferences.OnSharedPreferenceChangeListener)
}
