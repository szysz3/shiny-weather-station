package com.pinetreeapps.shinystationframe.page.alarm.fired

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import com.pinetreeapps.shinystationframe.dagger.AppDelegate
import com.pinetreeapps.shinystationframe.data.repository.WeatherStationApi
import com.pinetreeapps.shinystationframe.page.alarm.util.AlarmHelper
import com.pinetreeapps.shinystationframe.page.base.BasePresenterImpl
import com.pinetreeapps.shinystationframe.util.ValueFormatter
import javax.inject.Inject

class FiredAlarmPresenter : BasePresenterImpl<FiredAlarmContract.View>(),
                            FiredAlarmContract.Presenter {

    init {
        AppDelegate.graph.inject(this)
    }

    private val TAG = FiredAlarmPresenter::class.java.simpleName
    private val MAX_VOLUME = 20.0

    @Inject
    lateinit var context: Context

    @Inject
    lateinit var repository: WeatherStationApi

    private var mediaPlayer = MediaPlayer()
    private var currentVolume = 1.0

    override fun init(alarmTimestamp: Long, alarmId: String, alarmOffsetId: String) {
        view?.showAlarmTime(ValueFormatter.formatTimestampMillisToHour(alarmTimestamp))
        rescheduleAlarm(alarmId, alarmOffsetId)
        view?.setFullBrightness()
        view?.startBackgroundAnimation()
        playAlarm()
    }

    private fun playAlarm() {
        try {
            stopAlarm()

            val descriptor = context.assets.openFd("alarm_sound.mp3")
            mediaPlayer.setDataSource(descriptor.fileDescriptor,
                                      descriptor.startOffset,
                                      descriptor.length)
            descriptor.close()

            mediaPlayer.setOnCompletionListener {
                currentVolume = Math.min(currentVolume + 4, MAX_VOLUME - 1)
                playAlarm()
            }
            mediaPlayer.prepare()
            val alarmVolume =
                    (Math.log(MAX_VOLUME - currentVolume) / Math.log(MAX_VOLUME)).toFloat()

            mediaPlayer.setVolume(1 - alarmVolume, 1 - alarmVolume)
            mediaPlayer.start()
        } catch (e: Exception) {
            Log.d(TAG, "message: ${e.message}")
        }
    }

    private fun rescheduleAlarm(alarmId: String, alarmOffsetId: String) {
        val alarmList = repository.getAlarmList()
        AlarmHelper.rescheduleAlarm(context, alarmList, alarmId, alarmOffsetId)
    }

    override fun detachView() {
        stopAlarm()
        super.detachView()
    }

    private fun stopAlarm() {
        mediaPlayer.stop()
        mediaPlayer.reset()
        mediaPlayer.release()
        mediaPlayer = MediaPlayer()
    }
}