package com.pinetreeapps.shinystationframe.page.frame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pinetreeapps.shinystationframe.R
import com.pinetreeapps.shinystationframe.page.base.BaseFragmentImpl

class FramePageFragment : BaseFragmentImpl<FramePageContract.View, FramePageContract.Presenter>(),
                          FramePageContract.View {

    override var presenter: FramePageContract.Presenter = FramePagePresenter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.framepage_fragment_view, container, false)
    }
}
