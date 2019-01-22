package com.pinetreeapps.shinystationframe.page.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import com.flaviofaria.kenburnsview.KenBurnsView
import com.pinetreeapps.shinystationframe.GlideApp
import com.pinetreeapps.shinystationframe.R
import com.pinetreeapps.shinystationframe.page.base.BaseActivityImpl
import com.pinetreeapps.shinystationframe.page.clock.ClockPageFragment
import com.pinetreeapps.shinystationframe.page.frame.FramePageFragment
import com.pinetreeapps.shinystationframe.page.weather.WeatherPageFragment
import com.pinetreeapps.shinystationframe.widget.WarningAlert
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivityImpl<MainActivityContract.View, MainActivityContract.Presenter>(),
                     MainActivityContract.View, WarningAlert.AlertHandler {

    companion object {
        const val REGULAR_ANIM_DURATION_MS: Long = 300
        const val SHORT_ANIM_DURATION_MS: Long = 150
    }

    private val warningAlert = WarningAlert()

    override var presenter: MainActivityContract.Presenter = MainActivityPresenter()
    private var imgToBlur: KenBurnsView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        warningAlert.alertBtnHandler = this
        presenter.fetchPhotoList()
    }

    override fun onNoDataError() {
        warningAlert.isCancelable = false
        warningAlert.show(supportFragmentManager, WarningAlert.TAG)
    }

    override fun showNextPhoto(url: String) {
        imgToBlur?.pause()

        if (frontPhotoView.alpha == 1f) {
            switchPhoto(backPhotoView, frontPhotoView, url)
        } else {
            switchPhoto(frontPhotoView, backPhotoView, url)
        }

        imgToBlur?.resume()
        blurOverlay.setViewToBlur(imgToBlur as View)
        blurOverlay.invalidate()
    }

    override fun setPhotoFrameDisplayMode() {
        blurOverlay.postDelayed({
                                    blurOverlay.animate().alpha(0f).withStartAction {
                                        blurOverlay.pauseBlur()
                                        imgToBlur?.resume()
                                    }.withEndAction {
                                        blurOverlay.visibility = View.GONE
                                    }.start()
                                }, REGULAR_ANIM_DURATION_MS)
        dimOverlay.postDelayed({
                                   dimOverlay.animate().alpha(0f).start()
                               }, REGULAR_ANIM_DURATION_MS)
    }

    override fun setWeatherDisplayMode() {
        setNonFrameDisplayMode(0.5f)
    }

    override fun setClockDisplayMode() {
        setNonFrameDisplayMode(1f)
    }

    override fun onPhotoListFetched(photoList: List<String>) {
        loadPhotos()
        initBlur()
        initPager()

        presenter.onViewReady()
    }

    override fun onPositiveBtnTouched() {
        warningAlert.dismiss()
        presenter.fetchPhotoList()
    }

    private fun switchPhoto(frontPhoto: KenBurnsView, backPhoto: KenBurnsView, url: String) {
        frontPhoto.animate().setDuration(SHORT_ANIM_DURATION_MS).alpha(1f).withStartAction {
            frontPhoto.visibility = View.VISIBLE
        }.withEndAction { frontPhoto.resume() }.start()

        backPhoto.animate().setDuration(SHORT_ANIM_DURATION_MS).alpha(0f).withEndAction {
            backPhoto.visibility = View.INVISIBLE
        }.withStartAction { backPhoto.pause() }.start()

        imgToBlur = frontPhoto

        GlideApp.with(this@MainActivity).load(url).into(backPhoto)
    }

    private fun setNonFrameDisplayMode(alpha: Float) {
        imgToBlur?.pause()

        blurOverlay.postDelayed({
                                    blurOverlay.animate().alpha(1f).withStartAction {
                                        blurOverlay.visibility = View.VISIBLE
                                        blurOverlay.invalidate()
                                    }.withEndAction {
                                        blurOverlay.startBlur()
                                    }.start()
                                }, REGULAR_ANIM_DURATION_MS)
        dimOverlay.postDelayed({
                                   dimOverlay.animate().alpha(alpha).start()
                               }, REGULAR_ANIM_DURATION_MS)
    }

    private fun loadPhotos() {
        GlideApp.with(this).load(presenter.getCurrentPhotoUrl()).into(frontPhotoView)
        GlideApp.with(this).load(presenter.getNextPhotoUrl()).into(backPhotoView)
    }

    private fun initBlur() {
        imgToBlur = frontPhotoView
        imgToBlur?.pause()
        imgToBlur.let { blurOverlay.setViewToBlur(it as View) }
    }

    private fun initPager() {
        pager.offscreenPageLimit = 2
        pager.adapter = PageAdapter(supportFragmentManager)
        pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
                //intentionally left blank
            }

            override fun onPageScrolled(position: Int, positionOffset: Float,
                                        positionOffsetPixels: Int) {
                //intentionally left blank
            }

            override fun onPageSelected(position: Int) {
                blurOverlay.invalidate()

                val displayMode = MainActivityContract.DisplayMode.fromInt(position)
                displayMode?.let { presenter.onDisplayModeSwitched(it) }
            }
        })
    }

    class PageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            when (position) {
                0 -> return ClockPageFragment()
                1 -> return WeatherPageFragment()
            }

            return FramePageFragment()
        }

        override fun getCount(): Int {
            return 3
        }
    }
}
