package com.pinetreeapps.shinystationframe.widget

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.widget.LinearLayout
import com.pinetreeapps.numbertweening.widget.NumberView
import com.pinetreeapps.shinystationframe.R
import kotlinx.android.synthetic.main.clock_view.view.*
import java.util.*

class Clock : LinearLayout {

    private val uiHandler = Handler(Looper.getMainLooper())
    private val runnable: Runnable = Runnable { tick() }

    private val timer = Timer()
    private val timerTask = ClockTimerTask()
    private var timestampAtStart: Long = 0

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context,
                                                                                   attrs,
                                                                                   defStyleAttr) {
        init()
    }

    fun onDestroy() {
        timer.cancel()
        timer.purge()
    }

    private fun init() {
        inflate(context, R.layout.clock_view, this)

        secondNumber1.strokeWidth = 5f
        secondNumber2.strokeWidth = 5f

        tick()

        timestampAtStart = System.currentTimeMillis()
        timer.scheduleAtFixedRate(timerTask, 1000, 1000)
    }

    private fun tick() {
        val calendar = Calendar.getInstance()

        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        val second = calendar.get(Calendar.SECOND)

        setNumberValue(hour, hourNumber1, hourNumber2)
        setNumberValue(minute, minuteNumber1, minuteNumber2)
        setNumberValue(second, secondNumber1, secondNumber2)
    }

    private fun setNumberValue(value: Int, firstNumber: NumberView, secondNumber: NumberView,
                               animate: Boolean = true) {
        val firstDigit = value / 10
        val secondDigit = value % 10
        if (animate) {
            firstNumber.animate(firstNumber.endNumber, firstDigit).start()
            secondNumber.animate(secondNumber.endNumber, secondDigit).start()
        } else {
            firstNumber.animate(firstDigit).start()
            secondNumber.animate(secondDigit).start()
        }
    }

    inner class ClockTimerTask : TimerTask() {
        override fun run() {
            uiHandler.post(runnable)
        }
    }
}