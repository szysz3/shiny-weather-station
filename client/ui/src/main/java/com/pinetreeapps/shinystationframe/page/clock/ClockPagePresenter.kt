package com.pinetreeapps.shinystationframe.page.clock

import android.content.Context
import android.content.SharedPreferences
import com.pinetreeapps.shinystationframe.dagger.AppDelegate
import com.pinetreeapps.shinystationframe.data.repository.WeatherStationApi
import com.pinetreeapps.shinystationframe.data.repository.local.LocalStorageRepository
import com.pinetreeapps.shinystationframe.page.alarm.util.AlarmHelper
import com.pinetreeapps.shinystationframe.page.base.BasePresenterImpl
import com.pinetreeapps.shinystationframe.util.ValueFormatter
import java.lang.StringBuilder
import javax.inject.Inject

class ClockPagePresenter : BasePresenterImpl<ClockPageContract.View>(),
                           ClockPageContract.Presenter {

    init {
        AppDelegate.graph.inject(this)
    }

    @Inject
    lateinit var context: Context
    @Inject
    lateinit var repository: WeatherStationApi
    private val alarmsChangedListener: SharedPreferences.OnSharedPreferenceChangeListener =
            SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
                if (key == LocalStorageRepository.ALARM_LIST_KEY) {
                    getAlarmTime()
                }
            }

    override fun getAlarmTime() {
        val alarmList = repository.getAlarmList()?.asIterable()
        val stringAlarmRepresentation = StringBuilder()
        alarmList?.forEach {alarmModel ->
            if(AlarmHelper.isAlarmScheduled(context, alarmModel) && alarmModel.isEnabled) {
                if(stringAlarmRepresentation.isNotEmpty()) {
                    stringAlarmRepresentation.append("/")
                }
                stringAlarmRepresentation.append(ValueFormatter.formatIntWithLeadingZero(alarmModel.alarmHour) +
                        ":${ValueFormatter.formatIntWithLeadingZero(alarmModel.alarmMinute)}")
            }
        }

        view?.showAlarmTime(stringAlarmRepresentation.toString())
    }

    override fun attachView(view: ClockPageContract.View) {
        super.attachView(view)
        repository.registerLocalDataChangedListener(alarmsChangedListener)
    }

    override fun detachView() {
        super.detachView()
        repository.unregisterLocalDataChangedListener(alarmsChangedListener)
    }
}