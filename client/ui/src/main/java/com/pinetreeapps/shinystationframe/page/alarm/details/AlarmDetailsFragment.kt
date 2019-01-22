package com.pinetreeapps.shinystationframe.page.alarm.details

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.pinetreeapps.shinystationframe.R
import com.pinetreeapps.shinystationframe.data.model.AlarmModel
import com.pinetreeapps.shinystationframe.page.base.BaseFragmentImpl
import com.pinetreeapps.shinystationframe.widget.FuturaCheckBox
import kotlinx.android.synthetic.main.alarm_details_fragment_view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.joda.time.DateTimeConstants

class AlarmDetailsFragment :
        BaseFragmentImpl<AlarmDetailsContract.View, AlarmDetailsContract.Presenter>(),
        AlarmDetailsContract.View {

    companion object {
        val TAG: String = AlarmDetailsFragment.javaClass.simpleName

        private const val ALARM_ID_KEY = "ALARM_ID_KEY"

        @JvmStatic
        fun newInstance(alarmId: Int) = AlarmDetailsFragment().apply {
            arguments = Bundle().apply {
                putInt(ALARM_ID_KEY, alarmId)
            }
        }
    }

    private val dayOfWeekWidgetList: MutableMap<Int, FuturaCheckBox> =
            mutableMapOf()

    private var backButton: ImageView? = null
    private var saveButton: ImageView? = null
    override var presenter: AlarmDetailsContract.Presenter = AlarmDetailsPresenter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.alarm_details_fragment_view, container, false)
        populateDaysMap(view)

        backButton = view?.findViewById(R.id.back_image)
        backButton?.setOnClickListener {
            fragmentManager?.popBackStack()
        }
        saveButton = view?.findViewById(R.id.save_image)
        saveButton?.setOnClickListener {
            val selectedDays = getSelectedDays()
            val alarmSet = selectedDays.count() > 0
            GlobalScope.launch(Dispatchers.Default) {
                if(alarmSet) {
                    presenter.saveAlarm(time_picker.hour, time_picker.minute, selectedDays)
                }
            }
            if(alarmSet) {
                fragmentManager?.popBackStack()
            }
        }

        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        arguments?.getString(ALARM_ID_KEY)?.let {
            presenter.getAlarm(it)
        }
    }

    override fun showAlarmDetails(alarm: AlarmModel?) {

    }

    private fun populateDaysMap(view: View) {
        dayOfWeekWidgetList[DateTimeConstants.MONDAY] = view.findViewById(R.id.monday)
        dayOfWeekWidgetList[DateTimeConstants.TUESDAY] = view.findViewById(R.id.tuesday)
        dayOfWeekWidgetList[DateTimeConstants.WEDNESDAY] = view.findViewById(R.id.wednesday)
        dayOfWeekWidgetList[DateTimeConstants.THURSDAY] = view.findViewById(R.id.thursday)
        dayOfWeekWidgetList[DateTimeConstants.FRIDAY] = view.findViewById(R.id.friday)
        dayOfWeekWidgetList[DateTimeConstants.SATURDAY] = view.findViewById(R.id.saturday)
        dayOfWeekWidgetList[DateTimeConstants.SUNDAY] = view.findViewById(R.id.sunday)
    }

    private fun getSelectedDays(): List<Int> {
        val selectedDaysList = mutableListOf<Int>()
        dayOfWeekWidgetList.forEach {
            if (it.value.isChecked) {
                selectedDaysList.add(it.key)
            }
        }

        return selectedDaysList
    }
}