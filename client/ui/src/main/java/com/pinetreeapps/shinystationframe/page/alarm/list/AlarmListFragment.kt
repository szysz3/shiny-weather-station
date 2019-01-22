package com.pinetreeapps.shinystationframe.page.alarm.list

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.pinetreeapps.shinystationframe.R
import com.pinetreeapps.shinystationframe.data.model.AlarmModel
import com.pinetreeapps.shinystationframe.page.alarm.details.AlarmDetailsFragment
import com.pinetreeapps.shinystationframe.page.base.BaseFragmentImpl
import kotlinx.android.synthetic.main.alarm_list_fragment_view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AlarmListFragment : BaseFragmentImpl<AlarmListContract.View, AlarmListContract.Presenter>(),
                          AlarmListContract.View,
                          RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    companion object {

        @JvmStatic
        fun newInstance(): AlarmListFragment {
            return AlarmListFragment()
        }
    }

    override var presenter: AlarmListContract.Presenter = AlarmListPresenter()
    private var recyclerView: RecyclerView? = null
    private var backButton: ImageView? = null
    private var addAlarmButton: ImageView? = null
    private var alarmAdapter: AlarmAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.alarm_list_fragment_view, container, false)
        recyclerView = view.findViewById(R.id.alarm_list_recycler_view)
        recyclerView?.layoutManager = LinearLayoutManager(context)
        recyclerView?.addItemDecoration(AlarmItemDecoration(context))
        ItemTouchHelper(RecyclerItemTouchHelper(0,
                                                ItemTouchHelper.LEFT,
                                                this)).attachToRecyclerView(recyclerView)

        backButton = view.findViewById(R.id.back_image)
        backButton?.setOnClickListener {
            activity?.finish()
        }
        addAlarmButton = view.findViewById(R.id.add_alarm)
        addAlarmButton?.setOnClickListener {
            showAlarmDetailsFragment(0)
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        GlobalScope.launch(Dispatchers.Default) {
            presenter.getAlarms()
        }
    }

    override fun showAlarms(alarmList: MutableList<AlarmModel>) {
        loading_indicator.visibility = View.GONE

        alarmAdapter = AlarmAdapter(alarmList)
        alarmAdapter?.alarmRemovedListener = presenter as AlarmAdapter.AlarmRemovedListener
        recyclerView?.adapter = alarmAdapter

    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int, position: Int?) {
        alarmAdapter?.onSwiped(viewHolder, direction, position)
    }

    override fun showLoadingIndicator() {
        loading_indicator.visibility = View.VISIBLE
    }

    private fun showAlarmDetailsFragment(alarmId: Int) {
        fragmentManager?.beginTransaction()
                ?.setCustomAnimations(R.animator.slide_in_left,
                                      R.animator.slide_out_right,
                                      R.animator.slide_in_left,
                                      R.animator.slide_out_right)
                ?.add(R.id.fragment_container, AlarmDetailsFragment.newInstance(alarmId))
                ?.addToBackStack(AlarmDetailsFragment.TAG)?.commit()
    }
}
