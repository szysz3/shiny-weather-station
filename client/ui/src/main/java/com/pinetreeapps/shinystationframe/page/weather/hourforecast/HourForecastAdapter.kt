package com.pinetreeapps.shinystationframe.page.weather.hourforecast

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pinetreeapps.shinystationframe.R
import com.pinetreeapps.shinystationframe.dagger.AppDelegate
import com.pinetreeapps.shinystationframe.data.model.HourForecastModel
import com.pinetreeapps.shinystationframe.util.IconProvider
import com.pinetreeapps.shinystationframe.util.ValueFormatter
import javax.inject.Inject

class HourForecastAdapter(private val dataSource: List<HourForecastModel>) :
        RecyclerView.Adapter<HourForecastViewHolder>() {

    init {
        AppDelegate.graph.inject(this)
    }

    @Inject
    lateinit var iconProvider: IconProvider

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourForecastViewHolder {
        val hourForecastItemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.view_hour_forecast, parent, false)

        return HourForecastViewHolder(hourForecastItemView)
    }

    override fun onBindViewHolder(holder: HourForecastViewHolder, position: Int) {
        holder.timeText.text = ValueFormatter.formatTimestampSecondsToHour(dataSource[position].hour)
        holder.tempText.text = ValueFormatter.formatTemperature(dataSource[position].temp)
        holder.tempForecastIcon.setImageDrawable(iconProvider.getIcon(dataSource[position].iconId))
        holder.separator.visibility = if (position == itemCount - 1) View.GONE else View.VISIBLE
    }


    override fun getItemCount(): Int {
        return dataSource.size
    }

}