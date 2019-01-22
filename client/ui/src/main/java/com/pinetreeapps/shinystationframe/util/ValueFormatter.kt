package com.pinetreeapps.shinystationframe.util

import java.text.DateFormat
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.roundToInt

object ValueFormatter {

    private const val degreeSymbol = '\u00B0'
    private const val valueFormat = "#0.0"
    private const val dayOfWeekFormat = "EEEE"
    private const val percentSymbol = "%"

    private const val safePM25Value = 25
    private const val safePM10Value = 50

    @Synchronized
    fun formatTemperature(temp: Double): String {
        return "${DecimalFormat(valueFormat).format(temp)}$degreeSymbol"
    }

    @Synchronized
    fun formatTimestampMillisToHour(timestampInMillis: Long): String {
        val timestampDate = Date(timestampInMillis)
        return DateFormat.getTimeInstance(DateFormat.SHORT, Locale.getDefault())
                .format(timestampDate)
    }

    @Synchronized
    fun formatTimestampSecondsToHour(timestampInSeconds: Long): String {
        val timestampDate = Date(TimeUnit.SECONDS.toMillis(timestampInSeconds))
        return DateFormat.getTimeInstance(DateFormat.SHORT, Locale.getDefault())
                .format(timestampDate)
    }

    @Synchronized
    fun formatTimeStampToDayName(timestampInSeconds: Long): String {
        val timestampDate = Date(TimeUnit.SECONDS.toMillis(timestampInSeconds))
        return SimpleDateFormat(dayOfWeekFormat, Locale.getDefault()).format(timestampDate)
    }

    @Synchronized
    fun formatValueWithUnit(value: Double, unitSymbol: String): String {
        return "${DecimalFormat(valueFormat).format(value)} $unitSymbol"
    }

    @Synchronized
    fun formatPM10Value(pm10: Double): String {
        return "${((pm10 / safePM10Value) * 100).roundToInt()} $percentSymbol"
    }

    @Synchronized
    fun formatPM25Value(pm25: Double): String {
        return "${((pm25 / safePM25Value) * 100).roundToInt()} $percentSymbol"
    }

    @Synchronized
    fun formatIntWithLeadingZero(digit: Int): String {
        return String.format("%02d", digit)
    }
}