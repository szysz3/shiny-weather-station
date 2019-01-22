package com.pinetreeapps.shinystationframe.page.weather

import com.pinetreeapps.shinystationframe.dagger.AppDelegate
import com.pinetreeapps.shinystationframe.data.repository.WeatherStationApi
import com.pinetreeapps.shinystationframe.page.base.BasePresenterImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class WeatherPagePresenter : BasePresenterImpl<WeatherPageContract.View>(),
                             WeatherPageContract.Presenter {
    init {
        AppDelegate.graph.inject(this)
    }

    @Inject
    lateinit var repository: WeatherStationApi

    override fun fetchWeatherData() {
        repository.getCurrentConditions().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe({ result ->
                                                            view?.showWeatherData(result)
                                                            view?.hideLoadingIndicator()
                                                        }, {
                                                            view?.hideLoadingIndicator()
                                                            view?.showWarningAlert()
                                                        })
    }
}
