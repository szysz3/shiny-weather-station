package com.pinetreeapps.shinystationframe.data.repository.remote

import com.pinetreeapps.shinystationframe.data.model.PhotoModel
import com.pinetreeapps.shinystationframe.data.model.WeatherConditionsModel
import io.reactivex.Observable

class RemoteRepository : RemoteApi {

    private val apiService = WeatherWebServiceApi.create()

    override fun getCurrentConditions(): Observable<WeatherConditionsModel> {
        return apiService.getWeatherConditions()
    }

    override fun getPhotoList(): Observable<PhotoModel> {
        return apiService.getPhotoList()
    }
}