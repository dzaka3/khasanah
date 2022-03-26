package com.khasanah

import android.util.Log

class Static {

    companion object {
        lateinit var strLib: String
        init {
            strLib = "native-lib"
            System.loadLibrary( "native-lib")
        }

        fun initLib(){
            Log.d("Static","$strLib initiated")
        }
    }
}