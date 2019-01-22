package com.pinetreeapps.shinystationframe.data.model

import com.google.gson.annotations.SerializedName

data class HourForecastModel(val hour: Long, val temp: Double, @SerializedName("icon_id")
val iconId: String)