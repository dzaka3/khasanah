package com.khasanah

import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.khasanah.features.utils.*
import com.khasanah.R
import kotlinx.android.synthetic.main.activity_main.*
import org.apache.commons.lang3.StringUtils
import org.koin.android.ext.android.inject
import java.util.*


class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName
    private val prefs by inject<SharedPreferences>()
    private val signature by inject<SignatureHelper>()
    var n =1

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""
//        Static.initLib()

        getDeviceID()

        /*
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q &&
                !Settings.canDrawOverlays(applicationContext))
        {RequestPermission()}

         */
//        userInterface()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    fun getDeviceID(){
        val editor = prefs.edit()

        if (prefs.getString(Constant.KEY_DEVICE_ID, "").isNullOrEmpty() || StringUtils.equals(prefs.getString(
                Constant.KEY_DEVICE_ID, ""), "PREF_UNIQUE_ID")) {
            val deviceId = DeviceInfoUtils.getIMEI(this@MainActivity)
            editor.putString(Constant.KEY_DEVICE_ID, deviceId)
            editor.apply()
            /*
            val flag: Boolean = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this) == com.google.android.gms.common.ConnectionResult.SUCCESS
            if (!flag) {
                val deviceId = DeviceInfoUtils.getIMEI(this@MainActivity)
                editor.putString(Constant.KEY_DEVICE_ID, deviceId)
                editor.apply()
            }
            */
        }
        editor.apply()
    }

    /**
     * Observe network changes i.e. Internet Connectivity
     */
    private fun handleNetworkChanges() {
        NetworkUtils.getNetworkLiveData(applicationContext).observe(this, Observer { isConnected ->
            if (!isConnected) {
                ViewUtil.customSnackBar(parent_view,
                        getDrawable(R.drawable.ic_error),
                        resources.getString(R.string.common_no_internet_connection),
                        resources.getString(R.string.common_btn_oke), Snackbar.LENGTH_LONG)
            }
            /*else{
                Toast.makeText(App.appContext,
                        "connected",Toast.LENGTH_SHORT).show()
            }
             */
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        return Navigation.findNavController(this, R.id.nav_host_fragment).navigateUp()
    }

    override fun onBackPressed() {
        clearSnackbar()
        super.onBackPressed()
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        clearSnackbar()
        return super.dispatchTouchEvent(ev)
    }
    fun clearSnackbar(){
        if (ViewUtil.snackbar != null) {
            if (ViewUtil.snackbar!!.isShown){
                ViewUtil.snackbar!!.dismiss()
            }
        }
    }

}