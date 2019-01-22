package com.pinetreeapps.shinystationframe.widget

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import com.pinetreeapps.shinystationframe.R
import kotlinx.android.synthetic.main.view_alarm.view.*

class AlarmItem : ConstraintLayout {

    interface AlarmItemChangedListener {
        fun onAlarmItemEnabled(isEnabled: Boolean)
    }

    var alarmEnabled: Boolean
        get() = alarm_enabled.visibility == VISIBLE
        set(value) {
            if (value == alarmEnabled) return

            alarm_enabled.visibility = if (value) VISIBLE else GONE
            updateOverlay()
        }

    var alarmText: String
        get() = alarm_time.text.toString()
        set(value) {
            alarm_time.text = value
        }

    var alarmRepeatText: String
        get() = alarm_days.text.toString()
        set(value) {
            alarm_days.text = value
        }

    var foregroundView: View? = null

    var alarmItemChangedListener: AlarmItemChangedListener? = null

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context,
                                                                                  attrs,
                                                                                  defStyleAttr) {
        init(context)
    }

    private fun init(context: Context) {
        LayoutInflater.from(context).inflate(R.layout.view_alarm, this, true)
        foregroundView = foreground_view
        setOnClickListener {
            updateCheckmark()
            updateOverlay()
        }
    }

    private fun updateCheckmark() {
        alarm_enabled.visibility = if (alarm_enabled.visibility == VISIBLE) GONE else VISIBLE
    }

    private fun updateOverlay() {
        disabled_alarm_overlay.visibility =
                if (alarm_enabled.visibility == VISIBLE) GONE else VISIBLE
        notifyAlarmItemChanged(alarmEnabled)
    }

    private fun notifyAlarmItemChanged(isEnabled: Boolean) {
        alarmItemChangedListener?.onAlarmItemEnabled(isEnabled)
    }
}