package com.pinetreeapps.shinystationframe.page.alarm.list

import com.pinetreeapps.shinystationframe.data.model.AlarmModel
import com.pinetreeapps.shinystationframe.page.base.BasePresenter
import com.pinetreeapps.shinystationframe.page.base.BaseView

object AlarmListContract {

    interface View : BaseView {
        fun showAlarms(alarmList: MutableList<AlarmModel>)
        fun showLoadingIndicator()
    }

    interface Presenter : BasePresenter<View> {
        fun getAlarms()
    }
}