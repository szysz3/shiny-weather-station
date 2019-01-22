package com.pinetreeapps.shinystationframe.page.main

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.pinetreeapps.shinystationframe.dagger.AppDelegate
import com.pinetreeapps.shinystationframe.data.repository.WeatherStationApi
import com.pinetreeapps.shinystationframe.page.base.BasePresenterImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class MainActivityPresenter : BasePresenterImpl<MainActivityContract.View>(),
                              MainActivityContract.Presenter {
    companion object {
        const val PHOTO_SWITCH_DELAY_MS: Long = 15000
        const val SWITCH_DISPLAY_MODE_DELAY_MS: Long = 500
    }

    init {
        AppDelegate.graph.inject(this)
    }

    private val TAG = MainActivityPresenter::class.java.simpleName
    private val random = Random()
    private val uiHandler = Handler(Looper.getMainLooper())
    private val switchDisplayModeRunnable = SwitchDisplayModeRunnable()
    private val switchPhotoRunnable = SwitchPhotoRunnable()
    private var currentPhotoIndex = 0
    private var currentDisplayMode = MainActivityContract.DisplayMode.CLOCK
    private var newDisplayMode = currentDisplayMode
    private var photoList = listOf<String>()

    @Inject
    lateinit var repository: WeatherStationApi

    override fun getCurrentPhotoUrl(): String {
        return photoList[currentPhotoIndex]
    }

    override fun getNextPhotoUrl(): String {
        val tempArrayCount = photoList.count()
        return photoList[random.nextInt(tempArrayCount)]
    }

    override fun onDisplayModeSwitched(displayMode: MainActivityContract.DisplayMode) {
        newDisplayMode = displayMode
        uiHandler.removeCallbacks(switchPhotoRunnable)
        if (displayMode == MainActivityContract.DisplayMode.PHOTO_FRAME) {
            uiHandler.postDelayed(switchPhotoRunnable, PHOTO_SWITCH_DELAY_MS)
        }

        uiHandler.removeCallbacks(switchDisplayModeRunnable)
        uiHandler.postDelayed(switchDisplayModeRunnable, SWITCH_DISPLAY_MODE_DELAY_MS)
    }

    override fun onViewReady() {
        setRandomPhotoIndex()
        uiHandler.postDelayed(switchPhotoRunnable, PHOTO_SWITCH_DELAY_MS)
    }

    override fun fetchPhotoList() {
        repository.getPhotoList().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe({ result ->
                                                            photoList = result.photos
                                                            view?.onPhotoListFetched(result.photos)
                                                        }, { err ->
                                                            view?.onNoDataError()
                                                            Log.e(TAG, err.message)
                                                        })
    }

    private fun setRandomPhotoIndex() {
        currentPhotoIndex = random.nextInt(photoList.count())
    }

    inner class SwitchPhotoRunnable : Runnable {
        override fun run() {
            if (currentDisplayMode == MainActivityContract.DisplayMode.PHOTO_FRAME) {
                view?.showNextPhoto(getNextPhotoUrl())
                setRandomPhotoIndex()
            }
            uiHandler.postDelayed(this, PHOTO_SWITCH_DELAY_MS)
        }
    }

    inner class SwitchDisplayModeRunnable : Runnable {
        override fun run() {
            if (currentDisplayMode == newDisplayMode) return
            when (newDisplayMode) {
                MainActivityContract.DisplayMode.PHOTO_FRAME -> view?.setPhotoFrameDisplayMode()
                MainActivityContract.DisplayMode.WEATHER     -> view?.setWeatherDisplayMode()
                MainActivityContract.DisplayMode.CLOCK       -> view?.setClockDisplayMode()
            }

            currentDisplayMode = newDisplayMode
        }
    }
}
