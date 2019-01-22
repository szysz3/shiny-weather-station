package com.pinetreeapps.shinystationframe.data.model

import com.google.gson.annotations.SerializedName

data class ForecastModel(@SerializedName("day_forecast") val dayForecast: List<DayForecastModel>,
                         @SerializedName("hour_forecast") val hourForecast: List<HourForecastModel>)