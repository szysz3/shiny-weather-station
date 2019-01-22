package com.pinetreeapps.shinystationframe.page.base

interface BasePresenter<in V : BaseView> {

    fun attachView(view: V)

    fun detachView()
}