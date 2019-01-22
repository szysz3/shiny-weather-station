package com.pinetreeapps.shinystationframe.data.model

import com.google.gson.annotations.SerializedName

data class DayForecastModel(val day: Long, @SerializedName("temp_max") val maxTemp: Double,
                            @SerializedName("temp_min") val minTemp: Double,
                            @SerializedName("icon_id") val iconId: String)