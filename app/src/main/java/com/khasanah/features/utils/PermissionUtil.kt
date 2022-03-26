package com.khasanah.features.utils

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.khasanah.App

object PermissionUtil : AppCompatActivity() {
    val REQUEST_CODE_PERMISSIONS = 1001
    val CAMERA_PERMISSIONS = arrayOf("android.permission.CAMERA",
            "android.permission.WRITE_EXTERNAL_STORAGE")

    val READ_PERMISSIONS = arrayOf(
            "android.permission.READ_EXTERNAL_STORAGE")

    val WRITE_PERMISSIONS = arrayOf(
            "android.permission.WRITE_EXTERNAL_STORAGE")

    val LOCATION_PERMISSIONS = arrayOf("android.permission.ACCESS_FINE_LOCATION",
            "android.permission.ACCESS_COARSE_LOCATION")

    fun cameraPermissionsGranted(): Boolean {
        for (permission in CAMERA_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(App.appContext!!, permission) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    fun readPermissionsGranted(): Boolean {
        for (permission in READ_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(App.appContext!!, permission) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    fun writePermissionsGranted(): Boolean {
        for (permission in WRITE_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(App.appContext!!, permission) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    fun locationPermissionsGranted(): Boolean {
        for (permission in LOCATION_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(App.appContext!!, permission) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

}