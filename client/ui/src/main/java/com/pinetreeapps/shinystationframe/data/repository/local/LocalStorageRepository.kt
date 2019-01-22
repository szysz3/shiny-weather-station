package com.pinetreeapps.shinystationframe.data.repository.local

import android.content.Context
import android.content.SharedPreferences
import com.beust.klaxon.Klaxon
import com.pinetreeapps.shinystationframe.data.SharedPreferencesHelper
import com.pinetreeapps.shinystationframe.data.model.AlarmModel
import java.io.StringReader

class LocalStorageRepository(var context: Context) : LocalStorageApi {

    companion object {
        var ALARM_LIST_KEY = "ALARM_LIST_KEY"
    }

    private val localStorage: SharedPreferencesHelper = SharedPreferencesHelper(context)
    private val parser: Klaxon = Klaxon()

    override fun getAlarmList(): MutableList<AlarmModel>? {
        val alarmListRaw = localStorage.getString(ALARM_LIST_KEY, "[]")
        val mutableArray = parser.parseFromJsonArray<AlarmModel>(parser.parseJsonArray(StringReader(alarmListRaw)))
        return if (!alarmListRaw.isEmpty()) mutableArray?.toMutableList() else null
    }

    override fun saveAlarmList(alarmList: MutableList<AlarmModel>) {
        val alarmListRaw = parser.toJsonString(alarmList)
        localStorage.putString(ALARM_LIST_KEY, alarmListRaw)
    }

    override fun registerLocalDataChangedListener(
            listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        localStorage.registerListener(listener)
    }

    override fun unregisterLocalDataChangedListener(
            listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        localStorage.unregisterListener(listener)
    }
}
