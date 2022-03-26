package com.khasanah.features.utils

import okhttp3.Interceptor
import okhttp3.Response

class ServerErrorInterceptor : Interceptor {
    var redirect = false
    override fun intercept(chain: Interceptor.Chain) : Response  {
        val request = chain.request()
        val response = chain.proceed(request)
        //TODO dihandle disini atau di viewmodel,fragment
//        if (response.code == 500) {
//            UiThreadUtil.runOnUiThread {
//                ViewUtil.showSnackBarInfoFor500((MainActivity()),
//                        (MainActivity()).findViewById(R.id.parent_view),
//                        "Terjadi kesalahan di server,kami akan segera memperbaiki",
//                        "OK")
//            }
//            return response
//        }
//        if (response.code.equals(Constant.HTTP_STATUS_UNAUTHORIZED)) {
//            var intent = Intent(App.appContext, MainActivity::class.java)
//            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//            if (!redirect){
//                intent.putExtra(Constant.TOKEN_AUTH, Constant.TOKEN_AUTH_SESSION)
//                startActivity(App.appContext!!, intent, null)
//                redirect = true
//            } else {
//                redirect = false
//            }
//        }
        return response
    }
}