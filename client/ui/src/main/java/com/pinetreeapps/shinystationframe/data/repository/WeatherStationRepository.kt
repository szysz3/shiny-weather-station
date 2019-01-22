package com.pinetreeapps.shinystationframe.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.pinetreeapps.shinystationframe.data.model.AlarmModel
import com.pinetreeapps.shinystationframe.data.model.PhotoModel
import com.pinetreeapps.shinystationframe.data.model.WeatherConditionsModel
import com.pinetreeapps.shinystationframe.data.repository.local.LocalStorageApi
import com.pinetreeapps.shinystationframe.data.repository.local.LocalStorageRepository
import com.pinetreeapps.shinystationframe.data.repository.remote.RemoteApi
import com.pinetreeapps.shinystationframe.data.repository.remote.RemoteRepository
import io.reactivex.Observable

class WeatherStationRepository(var context: Context) : WeatherStationApi {

    private val remoteRepository: RemoteApi = RemoteRepository()
    private val localRepository: LocalStorageApi = LocalStorageRepository(context)

    override fun getAlarmList(): MutableList<AlarmModel>? {
        return localRepository.getAlarmList()
    }

    override fun saveAlarmList(alarmList: MutableList<AlarmModel>) {
        localRepository.saveAlarmList(alarmList)
    }

    override fun getCurrentConditions(): Observable<WeatherConditionsModel> {
        return remoteRepository.getCurrentConditions()
    }

    override fun getPhotoList(): Observable<PhotoModel> {
        return remoteRepository.getPhotoList()
    }

    override fun registerLocalDataChangedListener(
            listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        localRepository.registerLocalDataChangedListener(listener)
    }

    override fun unregisterLocalDataChangedListener(
            listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        localRepository.unregisterLocalDataChangedListener(listener)
    }
}
