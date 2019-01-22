package com.pinetreeapps.shinystationframe.page.alarm.main

import android.os.Bundle
import com.pinetreeapps.shinystationframe.R
import com.pinetreeapps.shinystationframe.page.alarm.list.AlarmListFragment
import com.pinetreeapps.shinystationframe.page.base.BaseActivityImpl

class AlarmPageActivity : BaseActivityImpl<AlarmPageContract.View, AlarmPageContract.Presenter>(),
                          AlarmPageContract.View {

    override var presenter: AlarmPageContract.Presenter = AlarmPagePresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)

        showAlarmListFragment()
    }

    private fun showAlarmListFragment() {
        supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, AlarmListFragment.newInstance()).commit()
    }
}
