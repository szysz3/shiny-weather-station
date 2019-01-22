package com.pinetreeapps.shinystationframe.page.base

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment

abstract class BaseFragmentImpl<in V : BaseView, T : BasePresenter<V>> : Fragment(), BaseView {

    protected abstract var presenter: T

    override fun getContext(): Context = activity as Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.attachView(this as V)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

}