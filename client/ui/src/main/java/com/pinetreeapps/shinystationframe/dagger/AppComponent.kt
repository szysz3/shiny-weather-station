package com.pinetreeapps.shinystationframe.dagger

import com.pinetreeapps.shinystationframe.page.alarm.details.AlarmDetailsPresenter
import com.pinetreeapps.shinystationframe.page.alarm.fired.FiredAlarmPresenter
import com.pinetreeapps.shinystationframe.page.alarm.list.AlarmListPresenter
import com.pinetreeapps.shinystationframe.page.clock.ClockPagePresenter
import com.pinetreeapps.shinystationframe.page.main.MainActivityPresenter
import com.pinetreeapps.shinystationframe.page.weather.WeatherPagePresenter
import com.pinetreeapps.shinystationframe.page.weather.dayforecast.DayForecastAdapter
import com.pinetreeapps.shinystationframe.page.weather.hourforecast.HourForecastAdapter
import com.pinetreeapps.shinystationframe.receiver.BootCompletedReceiver
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(application: AppDelegate)
    fun inject(adapter: HourForecastAdapter)
    fun inject(adapter: DayForecastAdapter)
    fun inject(weatherPagePresenter: WeatherPagePresenter)
    fun inject(presenter: MainActivityPresenter)
    fun inject(presenter: AlarmListPresenter)
    fun inject(presenter: AlarmDetailsPresenter)
    fun inject(presenter: ClockPagePresenter)
    fun inject(presenter: FiredAlarmPresenter)
    fun inject(bootCompletedReceiver: BootCompletedReceiver)
}