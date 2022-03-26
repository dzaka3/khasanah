package com.khasanah.features.utils

import android.content.Context
import com.khasanah.R

/**
 * to access resource in viewmodel
 */
class ResourcesHelper(private val applicationContext: Context) {

    val cannotConnectToServer
        get() = applicationContext.getString(R.string.common_cannot_connect_to_server)

    val timeOutConnetion
        get() = applicationContext.getString(R.string.common_timeout_connect_to_server)

    val noInternetConnection
        get() = applicationContext.getString(R.string.common_no_internet_connection)

    val cannotSaveData
        get() = applicationContext.getString(R.string.common_cannot_save_data)

    val errorSystem
        get() = applicationContext.getString(R.string.common_error_500)

    val errorGateway
        get() = applicationContext.getString(R.string.common_error_504)

    val forbidden
        get() = applicationContext.getString(R.string.common_error_forbidden)

    val badInput
        get() = applicationContext.getString(R.string.common_error_bad_input)

    val blockedByPinTrxAttemps
        get() = applicationContext.getString(R.string.common_error_blocked_by_pin_trx_attemps)

    val blockedByPwdAttemps
        get() = applicationContext.getString(R.string.common_error_blocked_by_pwd_attemps)

    val weakPassword
        get() = applicationContext.getString(R.string.common_error_weak_password)

    val dataNotFound
        get() = applicationContext.getString(R.string.common_error_data_not_found)

    val transactionLimit
        get() = applicationContext.getString(R.string.common_error_transaction_limit)

    val invalidCvv
        get() = applicationContext.getString(R.string.common_error_invalid_cvv)

    val invalidCvvLimit
        get() = applicationContext.getString(R.string.common_error_invalid_cvv_limit)

    val illegalArgumentException
        get() = applicationContext.getString(R.string.common_illegalStateException)

    val underMaintenance
        get() = applicationContext.getString(R.string.common_under_maintenance)

    val errorIst
        get() = applicationContext.getString(R.string.common_error_504_ist)

    val serverOverload
        get() = applicationContext.getString(R.string.common_error_429)

    val loginInactiveCard
        get() = applicationContext.getString(R.string.common_login_inactive_card)

    val trxDelayed
        get() = applicationContext.getString(R.string.common_timeout_trx)
}