package com.pinetreeapps.shinystationframe.page.alarm.fired

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.os.Bundle
import android.view.WindowManager
import com.pinetreeapps.shinystationframe.R
import com.pinetreeapps.shinystationframe.page.alarm.util.AlarmIntents
import com.pinetreeapps.shinystationframe.page.base.BaseActivityImpl
import kotlinx.android.synthetic.main.activity_fired_alarm.*


class FiredAlarmActivity :
        BaseActivityImpl<FiredAlarmContract.View, FiredAlarmContract.Presenter>(),
        FiredAlarmContract.View {

    override var presenter: FiredAlarmContract.Presenter = FiredAlarmPresenter()

    private var colorAnimator: Animator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fired_alarm)
        backgroundLayer.setOnClickListener {
            finish()
        }
        colorAnimator = AnimatorInflater.loadAnimator(this, R.animator.color_switch) as AnimatorSet

        val alarmTimestamp = intent.extras.getLong(AlarmIntents.ALARM_TIMESTAMP_KEY)
        val alarmId = intent.extras.getString(AlarmIntents.ALARM_ID_KEY)
        val alarmOffsetId = intent.extras.getString(AlarmIntents.ALARM_OFFSET_ID_KEY)

        presenter.init(alarmTimestamp, alarmId, alarmOffsetId)
    }

    override fun startBackgroundAnimation() {
        colorAnimator?.pause()
        colorAnimator?.end()

        colorAnimator?.setTarget(backgroundLayer)
        colorAnimator?.start()
    }

    override fun setFullBrightness() {
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        window.attributes.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_FULL
    }

    override fun showAlarmTime(alarmTime: String) {
        alarm_text.text = alarmTime
    }
}
