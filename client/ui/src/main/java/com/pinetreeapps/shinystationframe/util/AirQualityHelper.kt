package com.pinetreeapps.shinystationframe.util

import android.content.Context
import android.support.v4.content.ContextCompat
import com.pinetreeapps.shinystationframe.R
import com.pinetreeapps.shinystationframe.data.model.AirQualityModel

object AirQualityHelper {

    @Synchronized
    fun getAirQualitySpec(context: Context, airQualityIndex: Double): AirQualityModel {
        when (airQualityIndex) {
            in 0..50    -> return AirQualityModel(
                    ContextCompat.getColor(context, R.color.caqi_good),
                    context.getString(R.string.caqi_good_title),
                    context.getString(R.string.caqi_good))
            in 50..100  -> return AirQualityModel(
                    ContextCompat.getColor(context, R.color.caqi_moderate),
                    context.getString(R.string.caqi_moderate_title),
                    context.getString(R.string.caqi_moderate))
            in 100..150 -> return AirQualityModel(
                    ContextCompat.getColor(context, R.color.caqi_unhealthy_for_sensitive),
                    context.getString(R.string.caqi_unhealthy_for_sensitive_title),
                    context.getString(R.string.caqi_unhealthy_for_sensitive))
            in 150..200 -> return AirQualityModel(
                    ContextCompat.getColor(context, R.color.caqi_unhealthy),
                    context.getString(R.string.caqi_unhealthy_title),
                    context.getString(R.string.caqi_unhealthy))
            in 200..300 -> return AirQualityModel(
                    ContextCompat.getColor(context, R.color.caqi_very_unhealthy),
                    context.getString(R.string.caqi_very_unhealthy_title),
                    context.getString(R.string.caqi_very_unhealthy))
            in 300..500 -> return AirQualityModel(
                    ContextCompat.getColor(context, R.color.caqi_hazardous),
                    context.getString(R.string.caqi_hazardous_title),
                    context.getString(R.string.caqi_hazardous))
        }

        return AirQualityModel(ContextCompat.getColor(
                context,
                R.color.caqi_dead),
                                                                             context.getString(R.string.caqi_dead_title),
                                                                             context.getString(R.string.caqi_dead))
    }
}