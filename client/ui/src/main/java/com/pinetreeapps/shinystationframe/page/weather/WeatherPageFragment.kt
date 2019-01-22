package com.pinetreeapps.shinystationframe.page.weather

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pinetreeapps.shinystationframe.R
import com.pinetreeapps.shinystationframe.data.model.WeatherConditionsModel
import com.pinetreeapps.shinystationframe.page.base.BaseFragmentImpl
import com.pinetreeapps.shinystationframe.page.weather.dayforecast.DayForecastAdapter
import com.pinetreeapps.shinystationframe.page.weather.hourforecast.HourForecastAdapter
import com.pinetreeapps.shinystationframe.util.AirQualityHelper
import com.pinetreeapps.shinystationframe.util.ValueFormatter
import com.pinetreeapps.shinystationframe.widget.WarningAlert
import kotlinx.android.synthetic.main.weatherpage_fragment_view.*
import kotlin.math.roundToInt

class WeatherPageFragment :
        BaseFragmentImpl<WeatherPageContract.View, WeatherPageContract.Presenter>(),
        WeatherPageContract.View, WarningAlert.AlertHandler, SwipeRefreshLayout.OnRefreshListener {

    private val warningAlert = WarningAlert()
    override var presenter: WeatherPageContract.Presenter = WeatherPagePresenter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.weatherpage_fragment_view, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        warningAlert.alertBtnHandler = this
        presenter.fetchWeatherData()

        swipe_container.setOnRefreshListener(this)
        showLoadingIndicator()
    }

    override fun onPositiveBtnTouched() {
        warningAlert.dismiss()
        presenter.fetchWeatherData()
    }

    override fun showLoadingIndicator() {
        swipe_container.isRefreshing = true
        swipe_container.isEnabled = false
    }

    override fun hideLoadingIndicator() {
        swipe_container.isRefreshing = false
        swipe_container.isEnabled = true
    }

    override fun showWarningAlert() {
        warningAlert.isCancelable = false
        warningAlert.show(childFragmentManager, WarningAlert.TAG)
    }

    override fun showWeatherData(weatherData: WeatherConditionsModel) {
        val dayForecast = weatherData.forecast.dayForecast
        day_forecast_view.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        day_forecast_view.adapter = DayForecastAdapter(dayForecast)

        val hourForecast = weatherData.forecast.hourForecast
        hour_forecast_view.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        hour_forecast_view.adapter = HourForecastAdapter(hourForecast)

        val currentConditions = weatherData.currentConditions
        humidity_text.text = ValueFormatter.formatValueWithUnit(currentConditions.humidity,
                                                                currentConditions.humiditySymbol)
        pressure_text.text = ValueFormatter.formatValueWithUnit(currentConditions.pressure,
                                                                currentConditions.pressureUnit)
        pm10_text.text = ValueFormatter.formatPM10Value(currentConditions.pm10)
        pm25_text.text = ValueFormatter.formatPM25Value(currentConditions.pm25)
        pm10_internal_text.text = ValueFormatter.formatPM10Value(currentConditions.pm10Internal)
        pm25_internal_text.text = ValueFormatter.formatPM25Value(currentConditions.pm25Internal)
        current_temp_text.text = ValueFormatter.formatTemperature(currentConditions.temp)

        val airQualitySpec =
                AirQualityHelper.getAirQualitySpec(context, currentConditions.airQualityIndex)
        caqi_text.text = currentConditions.airQualityIndex.roundToInt().toString()
        caqi_value.setBackgroundColor(airQualitySpec.color)

        hideLoadingIndicator()
    }

    override fun onRefresh() {
        presenter.fetchWeatherData()
    }
}