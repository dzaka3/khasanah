package com.khasanah

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.*
import android.os.Build
import com.khasanah.features.di.appModule
import com.khasanah.features.di.repoModule
import com.khasanah.features.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        appContext = applicationContext
        //Stetho.initializeWithDefaults(this)
        startKoin {
            androidContext(this@App)
            modules(listOf(appModule, repoModule, viewModelModule))
        }
        val pm = packageManager

    }

    companion object {
        val CHANNEL_ID = "autoStartServiceChannel"
        val CHANNEL_NAME = "Auto Start Service Channel"
        var appContext: Context? = null
    }

    override fun unbindService(conn: ServiceConnection) {
        try {
            super.unbindService(conn)
        } catch (e: IllegalArgumentException) {
            // do something, ignore or report...
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }
}