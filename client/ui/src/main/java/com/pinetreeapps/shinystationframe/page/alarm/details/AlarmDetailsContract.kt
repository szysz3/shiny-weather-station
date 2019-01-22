package com.pinetreeapps.shinystationframe.page.alarm.details

import com.pinetreeapps.shinystationframe.data.model.AlarmModel
import com.pinetreeapps.shinystationframe.page.base.BasePresenter
import com.pinetreeapps.shinystationframe.page.base.BaseView

object AlarmDetailsContract {

    interface View : BaseView {
        fun showAlarmDetails(alarm: AlarmModel?)
    }

    interface Presenter : BasePresenter<View> {
        fun saveAlarm(hour: Int, minute: Int, days: List<Int>)
        fun getAlarm(alarmId: String)
    }
}
