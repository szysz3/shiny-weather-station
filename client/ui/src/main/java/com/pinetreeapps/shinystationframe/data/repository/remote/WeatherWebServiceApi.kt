package com.pinetreeapps.shinystationframe.data.repository.remote

import com.pinetreeapps.shinystationframe.data.model.PhotoModel
import com.pinetreeapps.shinystationframe.data.model.WeatherConditionsModel
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

interface WeatherWebServiceApi {

    @GET("/weather_conditions")
    fun getWeatherConditions(): Observable<WeatherConditionsModel>

    @GET("/photos")
    fun getPhotoList(): Observable<PhotoModel>

    companion object Factory {

        private const val TIMEOUT = 30L
        private const val BASE_URL = "[PUT YOUR RPI WEATHER STATION IP HERE]"

        fun create(): WeatherWebServiceApi {
            val retrofit =
                    Retrofit.Builder().addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create()).baseUrl(BASE_URL)
                            .client(OkHttpClient.Builder().readTimeout(TIMEOUT,
                                                                       TimeUnit.SECONDS).connectTimeout(
                                    TIMEOUT,
                                    TimeUnit.SECONDS).build()).build()

            return retrofit.create(WeatherWebServiceApi::class.java)
        }
    }
}