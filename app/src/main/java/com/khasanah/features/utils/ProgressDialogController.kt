package com.khasanah.features.utils

import android.app.Activity
import android.app.ProgressDialog
import com.khasanah.features.utils.component.CustomAVProgressDialog

object ProgressDialogController {
    private var progressDialog: ProgressDialog? = null
    fun showProgressDialog(activity: Activity?) {
        progressDialog = CustomAVProgressDialog.showProgressDialog(activity)
        progressDialog?.show()
    }

    fun hideProgressDialog() {
        progressDialog?.dismiss()
    }

    val isLoading: Boolean
        get() = if (progressDialog != null) {
            progressDialog!!.isShowing
        } else false
}