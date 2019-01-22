package com.pinetreeapps.shinystationframe.page.clock

import com.pinetreeapps.shinystationframe.page.base.BasePresenter
import com.pinetreeapps.shinystationframe.page.base.BaseView

object ClockPageContract {

    interface View : BaseView {
        fun showAlarmTime(alarmTime: String)
    }

    interface Presenter : BasePresenter<View> {
        fun getAlarmTime()
    }
}