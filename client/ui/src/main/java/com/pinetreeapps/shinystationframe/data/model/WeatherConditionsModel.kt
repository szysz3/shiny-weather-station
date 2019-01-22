package com.pinetreeapps.shinystationframe.data.model

import com.google.gson.annotations.SerializedName

data class WeatherConditionsModel(@SerializedName("current_conditions")
                                  val currentConditions: CurrentConditionsModel,
                                  val forecast: ForecastModel)