package com.pinetreeapps.shinystationframe.data

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

class SharedPreferencesHelper(val context: Context) {
    private var sharedPreferences: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(context)

    @SuppressLint("ApplySharedPref")
    fun putString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).commit()
    }

    fun getString(key: String, defaultValue: String): String {
        return sharedPreferences.getString(key, defaultValue)
    }

    fun registerListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)
    }

    fun unregisterListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener)
    }
}
