package com.pinetreeapps.shinystationframe.page.alarm.fired

import com.pinetreeapps.shinystationframe.page.base.BasePresenter
import com.pinetreeapps.shinystationframe.page.base.BaseView

object FiredAlarmContract {

    interface View : BaseView {
        fun startBackgroundAnimation()
        fun setFullBrightness()
        fun showAlarmTime(alarmTime: String)
    }

    interface Presenter : BasePresenter<View> {
        fun init(alarmTimestamp: Long, alarmId: String, alarmOffsetId: String)
    }
}