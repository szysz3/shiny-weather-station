package com.pinetreeapps.shinystationframe.page.weather.dayforecast

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.pinetreeapps.shinystationframe.R


class DayForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val dayText: TextView = itemView.findViewById(R.id.day_name) as TextView
    val forecastIcon: ImageView = itemView.findViewById(R.id.day_forecast_icon) as ImageView
    val minTempText: TextView = itemView.findViewById(R.id.day_forecast_min_temp) as TextView
    val maxTempText: TextView = itemView.findViewById(R.id.day_forecast_max_temp) as TextView
    val separator: View = itemView.findViewById(R.id.day_forecast_separator)
}