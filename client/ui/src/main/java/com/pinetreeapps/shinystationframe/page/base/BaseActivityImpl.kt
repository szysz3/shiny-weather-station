package com.pinetreeapps.shinystationframe.page.base

import android.content.Context
import android.os.Bundle
import android.support.v4.app.FragmentActivity

abstract class BaseActivityImpl<in V : BaseView, T : BasePresenter<V>> : FragmentActivity(),
                                                                         BaseView {
    override fun getContext(): Context = this

    protected abstract var presenter: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.attachView(this as V)
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

}