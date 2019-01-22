package com.pinetreeapps.shinystationframe.page.frame

import com.pinetreeapps.shinystationframe.page.base.BasePresenter
import com.pinetreeapps.shinystationframe.page.base.BaseView

object FramePageContract {

    interface View : BaseView

    interface Presenter : BasePresenter<View>
}