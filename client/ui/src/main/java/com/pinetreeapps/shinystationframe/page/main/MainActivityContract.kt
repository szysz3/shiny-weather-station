package com.pinetreeapps.shinystationframe.page.main

import com.pinetreeapps.shinystationframe.page.base.BasePresenter
import com.pinetreeapps.shinystationframe.page.base.BaseView

object MainActivityContract {

    enum class DisplayMode(val displayMode: Int) {
        CLOCK(0), WEATHER(1), PHOTO_FRAME(2);

        companion object {
            private val map = DisplayMode.values().associateBy(DisplayMode::displayMode)
            fun fromInt(type: Int) = map[type]
        }
    }

    interface View : BaseView {
        fun showNextPhoto(url: String)
        fun setPhotoFrameDisplayMode()
        fun setWeatherDisplayMode()
        fun setClockDisplayMode()
        fun onPhotoListFetched(photoList: List<String>)
        fun onNoDataError()
    }

    interface Presenter : BasePresenter<View> {
        fun getCurrentPhotoUrl(): String
        fun getNextPhotoUrl(): String
        fun onDisplayModeSwitched(displayMode: DisplayMode)
        fun onViewReady()
        fun fetchPhotoList()
    }
}