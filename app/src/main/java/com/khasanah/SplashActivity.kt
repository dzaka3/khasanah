package com.khasanah

import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.khasanah.R
import kotlinx.android.synthetic.main.activity_splash.*
import org.koin.android.ext.android.inject


class SplashActivity : AppCompatActivity() {
    private val prefs by inject<SharedPreferences>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        txt_versi.text = "Versi ${getVersionName()}"

//        NetworkUtils.getNetworkLiveData(this)
//            .observe(this, Observer {
//                if (it) {
//                    //if connected then sync
//                    GlobalScope.launch {
//                        //sync jadwala
//                        //initViewModel.syncData()
//                    }
//                }
//            })

        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)

    }

        fun getVersionName(): String {
            var v = ""
            try {
                v = packageManager.getPackageInfo(packageName, 0).versionName
            } catch (e: PackageManager.NameNotFoundException) {
            }
            return v
        }

    }
