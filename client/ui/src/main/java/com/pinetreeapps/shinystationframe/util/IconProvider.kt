package com.pinetreeapps.shinystationframe.util

import android.content.Context
import android.graphics.drawable.Drawable

class IconProvider(val context: Context) {
    companion object {
        const val DRAWABLE_KEY = "drawable"
        const val ICON_POSTFIX = "icon_"
    }

    @Synchronized
    fun getIcon(iconId: String): Drawable {
        val drawableId = context.resources.getIdentifier(getIconName(iconId),
                                                         DRAWABLE_KEY,
                                                         context.packageName)
        return context.getDrawable(drawableId)
    }

    private fun getIconName(iconId: String): String {
        return ICON_POSTFIX + iconId
    }
}