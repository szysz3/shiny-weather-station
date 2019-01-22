package com.pinetreeapps.shinystationframe.page.base

open class BasePresenterImpl<V : BaseView> : BasePresenter<V> {

    protected var view: V? = null

    override fun attachView(view: V) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }
}