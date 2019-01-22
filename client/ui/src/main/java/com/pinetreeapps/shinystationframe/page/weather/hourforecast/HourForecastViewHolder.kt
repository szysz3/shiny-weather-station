package com.pinetreeapps.shinystationframe.page.weather.hourforecast

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.pinetreeapps.shinystationframe.R

class HourForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val timeText: TextView = itemView.findViewById(R.id.time_text) as TextView
    val tempText: TextView = itemView.findViewById(R.id.temp_text) as TextView
    val tempForecastIcon: ImageView = itemView.findViewById(R.id.temp_forecast_icon) as ImageView
    val separator: View = itemView.findViewById(R.id.separator) as View
}