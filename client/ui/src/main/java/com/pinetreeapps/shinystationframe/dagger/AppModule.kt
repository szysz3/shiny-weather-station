package com.pinetreeapps.shinystationframe.dagger

import android.app.Application
import android.content.Context
import com.pinetreeapps.shinystationframe.data.repository.WeatherStationApi
import com.pinetreeapps.shinystationframe.data.repository.WeatherStationRepository
import com.pinetreeapps.shinystationframe.util.IconProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {

    @Provides
    @Singleton
    fun providesIconProvider(): IconProvider {
        return IconProvider(application)
    }

    @Provides
    @Singleton
    fun providesRepository(): WeatherStationApi {
        return WeatherStationRepository(application)
    }

    @Provides
    @Singleton
    fun providesContext(): Context {
        return application
    }
}