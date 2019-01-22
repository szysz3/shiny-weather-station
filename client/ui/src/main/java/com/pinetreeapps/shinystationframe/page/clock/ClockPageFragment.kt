package com.pinetreeapps.shinystationframe.page.clock

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.pinetreeapps.shinystationframe.R
import com.pinetreeapps.shinystationframe.page.alarm.main.AlarmPageActivity
import com.pinetreeapps.shinystationframe.page.base.BaseFragmentImpl
import kotlinx.android.synthetic.main.clockpage_fragment_view.*

class ClockPageFragment : BaseFragmentImpl<ClockPageContract.View, ClockPageContract.Presenter>(),
                          ClockPageContract.View {

    override var presenter: ClockPageContract.Presenter = ClockPagePresenter()
    private var alarmClock: TextView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.clockpage_fragment_view, container, false)
        alarmClock = view.findViewById(R.id.alarmclock_text)

        presenter.getAlarmTime()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        alarmclock_container.setOnClickListener {
            context.startActivity(Intent(context, AlarmPageActivity::class.java))
        }
    }

    override fun onDestroyView() {
        clock.onDestroy()
        super.onDestroyView()
    }

    override fun showAlarmTime(alarmTime: String) {
        alarmClock?.text = alarmTime
    }
}