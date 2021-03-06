package com.pinetreeapps.shinystationframe.widget

import android.content.Context
import android.support.v7.widget.AppCompatCheckBox
import android.util.AttributeSet
import com.pinetreeapps.shinystationframe.util.TypefaceHelper

class FuturaCheckBox : AppCompatCheckBox {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context,
                                                                                  attrs,
                                                                                  defStyleAttr) {
        init()
    }

    private fun init() {
        typeface = TypefaceHelper[context, TypefaceHelper.TYPEFACE_NAME]
    }
}
