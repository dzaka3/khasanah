package com.khasanah.features.utils

import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.khasanah.App
import com.khasanah.features.dto.SharedPrefsModel
import com.khasanah.features.utils.Constant.MASTER_KEY_ALIAS

object SharedPrefManager {

    fun initializePrefs(): SharedPreferences {
        val masterKey = MasterKey.Builder(App.appContext!!, MASTER_KEY_ALIAS).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()
//        val preferences = App.appContext!!.getSharedPreferences("shardprefs", Context.MODE_PRIVATE)
        val preferences = EncryptedSharedPreferences.create(
                App.appContext!!,
            Constant.SHARED_PREF_NAME,
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        return preferences
    }

    //this method will store the voucher data in shared preferences
    var sharedPrefsModel: SharedPrefsModel
        get() {
            val preferences = initializePrefs()
            val sharedPrefsModel = SharedPrefsModel()
            sharedPrefsModel.token = preferences.getString(Constant.KEY_TOKEN, null)
            return sharedPrefsModel
        }
        set(sharedPrefsModel) {
            val preferences = initializePrefs()
            val editor = preferences.edit()
            if (sharedPrefsModel.token != null) {
                editor.putString(Constant.KEY_TOKEN, sharedPrefsModel.token)
            }
            editor.apply()
        }
}