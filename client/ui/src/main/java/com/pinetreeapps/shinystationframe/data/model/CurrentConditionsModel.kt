package com.pinetreeapps.shinystationframe.data.model

import com.google.gson.annotations.SerializedName

data class CurrentConditionsModel(@SerializedName("name") val city: String, val temp: Double,
                                  @SerializedName("temp_max") val maxTemp: Double,
                                  @SerializedName("temp_min") val minTemp: Double,
                                  @SerializedName("description") val desc: String,
                                  @SerializedName("icon_id") val iconId: String,
                                  val humidity: Double, @SerializedName("humidity_symbol")
                                  val humiditySymbol: String, val pressure: Double,
                                  @SerializedName("pressure_unit") val pressureUnit: String,
                                  @SerializedName("pm25_internal") val pm25Internal: Double,
                                  @SerializedName("pm10_internal") val pm10Internal: Double,
                                  val pm10: Double, val pm25: Double, val airQualityIndex: Double,
                                  val pollutionLevel: Int)