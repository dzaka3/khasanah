package com.khasanah.features.utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import android.telephony.SubscriptionManager
import android.telephony.TelephonyManager
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import java.util.*

private val COLON = ":"

@SuppressLint("HardwareIds", "MissingPermission")
object DeviceInfoUtils {
    // ICCID (Integrated Circuit Card Identifier)
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    fun getICCID(context: Context): String? {
        return if (isPermissionGranted(context)) {
            if (isICCIDGranted) {
                val iccid = StringBuilder()
                val subscription = SubscriptionManager.from(context).activeSubscriptionInfoList
                        ?: return null
                for (info in subscription) {
                    //https://developer.android.com/about/versions/11/behavior-changes-all
                    iccid.append(info.subscriptionId)
                }
                if (iccid.toString().isEmpty()) null else iccid.toString()
            } else {
                //dual sim not support SDK < lollipop
                (context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager).simSerialNumber
            }
        } else null
    }

    // IMSI (International Mobile Subscriber Identity)
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    fun getIMSI(context: Context, firebaseId: String, rdm: String?=""): String {
        var iccid = getICCID(context)
        iccid = iccid ?: ""
        val imsi = getIMEI(context)
        val strTmp = imsi.split("-".toRegex()).toTypedArray()
        val length = strTmp.size
        val builder = StringBuilder()
        for (i in 0 until length) {
            if (i == length - 1) {
                strTmp[i] = firebaseId
            }
            if (i != 0) {
                builder.append("-")
            }
            builder.append(strTmp[i])
        }
        return builder.toString() +"-"+ rdm
    }

    // IMEI (International Mobile Equipment Identity)
    fun getIMEI(context: Context): String {
        return generateIMEI(context)
    }

    private fun isPermissionGranted(context: Context): Boolean {
        val wantPermission = Manifest.permission.READ_PHONE_STATE
        return ActivityCompat.checkSelfPermission(context, wantPermission) == PackageManager.PERMISSION_GRANTED
    }

    private val isICCIDGranted: Boolean
        private get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1

    private fun generateIMEI(context: Context): String {
        return "android".plus(COLON)
            .plus(getAndroidId(context)).plus(COLON)
            .plus(Build.BOARD).plus(COLON)
            .plus(Build.BRAND).plus(COLON)
            .plus(Build.DEVICE).plus(COLON)
            .plus(Build.DISPLAY).plus(COLON)
            .plus(Build.HOST).plus(COLON)
            .plus(Build.ID).plus(COLON)
            .plus(Build.MANUFACTURER).plus(COLON)
            .plus(Build.MODEL).plus(COLON)
            .plus(Build.PRODUCT).plus(COLON)
            .plus(Build.TAGS).plus(COLON)
            .plus(Build.TYPE).plus(COLON)
            .plus(Build.USER).plus(COLON)
            .plus(Calendar.getInstance().timeInMillis)
    }

    private fun getAndroidId(context: Context): String {
        val id = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        return id ?: ""
    }
}