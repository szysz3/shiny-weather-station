package com.pinetreeapps.shinystationframe.page.weather.dayforecast

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pinetreeapps.shinystationframe.R
import com.pinetreeapps.shinystationframe.dagger.AppDelegate
import com.pinetreeapps.shinystationframe.data.model.DayForecastModel
import com.pinetreeapps.shinystationframe.util.IconProvider
import com.pinetreeapps.shinystationframe.util.ValueFormatter
import javax.inject.Inject

class DayForecastAdapter(private val dataSource: List<DayForecastModel>) :
        RecyclerView.Adapter<DayForecastViewHolder>() {

    init {
        AppDelegate.graph.inject(this)
    }

    @Inject
    lateinit var iconProvider: IconProvider

    override fun onBindViewHolder(holder: DayForecastViewHolder, position: Int) {
        holder.dayText.text = ValueFormatter.formatTimeStampToDayName(dataSource[position].day)
        holder.minTempText.text = ValueFormatter.formatTemperature(dataSource[position].minTemp)
        holder.maxTempText.text = ValueFormatter.formatTemperature(dataSource[position].maxTemp)
        holder.forecastIcon.setImageDrawable(iconProvider.getIcon(dataSource[position].iconId))
        holder.separator.visibility =
                if (position == itemCount - 1) View.INVISIBLE else View.VISIBLE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayForecastViewHolder {
        val dayForecastItemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.view_day_forecast, parent, false)

        return DayForecastViewHolder(dayForecastItemView)
    }

    override fun getItemCount(): Int {
        return dataSource.size
    }
}