package com.pinetreeapps.shinystationframe.util

import android.content.Context
import android.graphics.Typeface
import android.util.Log
import java.util.*

object TypefaceHelper {

    const val TYPEFACE_NAME = "FuturaLight.ttf"

    private val TAG = TypefaceHelper::class.java.simpleName

    private val cache = Hashtable<String, Typeface>()

    operator fun get(c: Context, assetPath: String): Typeface? {
        if (!cache.containsKey(assetPath)) {
            try {
                val t = Typeface.createFromAsset(c.assets, assetPath)
                cache[assetPath] = t
            } catch (e: Exception) {
                Log.e(TAG, "Could not get typeface '" + assetPath + "' because: " + e.message)
                return null
            }

        }
        return cache[assetPath]
    }
}