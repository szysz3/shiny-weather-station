package com.pinetreeapps.shinystationframe.page.alarm.details

import android.content.Context
import com.pinetreeapps.shinystationframe.dagger.AppDelegate
import com.pinetreeapps.shinystationframe.data.repository.WeatherStationApi
import com.pinetreeapps.shinystationframe.page.alarm.util.AlarmHelper.createAndScheduleAlarm
import com.pinetreeapps.shinystationframe.page.base.BasePresenterImpl
import javax.inject.Inject


class AlarmDetailsPresenter : BasePresenterImpl<AlarmDetailsContract.View>(),
                              AlarmDetailsContract.Presenter {

    init {
        AppDelegate.graph.inject(this)
    }

    @Inject
    lateinit var repository: WeatherStationApi
    @Inject
    lateinit var context: Context

    override fun saveAlarm(hour: Int, minute: Int, days: List<Int>) {
        var alarmList = repository.getAlarmList() ?: mutableListOf()
        alarmList = createAndScheduleAlarm(context, hour, minute, days, alarmList)
        repository.saveAlarmList(alarmList)
    }

    override fun getAlarm(alarmId: String) {
        val alarm = repository.getAlarmList() ?: return
        view?.showAlarmDetails(alarm.firstOrNull { it.id == alarmId })
    }
}