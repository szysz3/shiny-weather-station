package com.pinetreeapps.shinystationframe.data.repository.remote

import com.pinetreeapps.shinystationframe.data.model.PhotoModel
import com.pinetreeapps.shinystationframe.data.model.WeatherConditionsModel
import io.reactivex.Observable

interface RemoteApi {
    fun getCurrentConditions(): Observable<WeatherConditionsModel>
    fun getPhotoList(): Observable<PhotoModel>
}
