package com.pinetreeapps.shinystationframe.page.alarm.list

import android.content.SharedPreferences
import com.pinetreeapps.shinystationframe.dagger.AppDelegate
import com.pinetreeapps.shinystationframe.data.model.AlarmModel
import com.pinetreeapps.shinystationframe.data.repository.WeatherStationApi
import com.pinetreeapps.shinystationframe.data.repository.local.LocalStorageRepository
import com.pinetreeapps.shinystationframe.page.base.BasePresenterImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class AlarmListPresenter : BasePresenterImpl<AlarmListContract.View>(), AlarmListContract.Presenter,
                           AlarmAdapter.AlarmRemovedListener {

    init {
        AppDelegate.graph.inject(this)
    }

    @Inject
    lateinit var repository: WeatherStationApi
    private val alarmsChangedListener: SharedPreferences.OnSharedPreferenceChangeListener =
            SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
                if (key == LocalStorageRepository.ALARM_LIST_KEY) {
                    getAlarms()
                }
            }

    override fun getAlarms() {
        view?.showLoadingIndicator()
        val alarmList = repository.getAlarmList() ?: mutableListOf()
        GlobalScope.launch(Dispatchers.Main) {
            view?.showAlarms(alarmList)
        }
    }

    override fun onAlarmRemoved(alarmList: MutableList<AlarmModel>) {
        repository.unregisterLocalDataChangedListener(alarmsChangedListener)
        repository.saveAlarmList(alarmList)
        repository.registerLocalDataChangedListener(alarmsChangedListener)
    }

    override fun attachView(view: AlarmListContract.View) {
        super.attachView(view)
        repository.registerLocalDataChangedListener(alarmsChangedListener)

    }

    override fun detachView() {
        super.detachView()
        repository.unregisterLocalDataChangedListener(alarmsChangedListener)
    }
}
