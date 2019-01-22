package com.pinetreeapps.shinystationframe.widget

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import com.pinetreeapps.shinystationframe.R

class WarningAlert : DialogFragment() {
    companion object {
        val TAG: String = WarningAlert::class.java.simpleName
    }

    interface AlertHandler {
        fun onPositiveBtnTouched()
    }

    var alertBtnHandler: AlertHandler? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity as Context)
        builder.setMessage(R.string.no_data_alert_text).setTitle(R.string.no_data_alert_title_text)
                .setPositiveButton(R.string.no_data_alert_positive_btn) { _, _ ->
                    alertBtnHandler?.onPositiveBtnTouched()
                }

        return builder.create()
    }
}