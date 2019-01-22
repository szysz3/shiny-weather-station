package com.pinetreeapps.shinystationframe.page.weather

import com.pinetreeapps.shinystationframe.data.model.WeatherConditionsModel
import com.pinetreeapps.shinystationframe.page.base.BasePresenter
import com.pinetreeapps.shinystationframe.page.base.BaseView

object WeatherPageContract {

    interface View : BaseView {
        fun showLoadingIndicator()
        fun hideLoadingIndicator()
        fun showWarningAlert()
        fun showWeatherData(weatherData: WeatherConditionsModel)
    }

    interface Presenter : BasePresenter<View> {
        fun fetchWeatherData()
    }
}