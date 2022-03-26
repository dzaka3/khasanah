package com.khasanah.features.di

import android.content.Context
import android.content.SharedPreferences
import com.khasanah.features.data.api.ApiService
import com.khasanah.features.utils.*
import com.squareup.moshi.Moshi
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

val appModule = module {
    single { provideSharedPrefManager() }
    single { provideOkHttpClient(get()) }
    single { provideRetrofit(get(), Constant.BASE_URL) }
    single { provideApiService(get()) }
    single { provideNetworkHelper(androidContext()) }
    single { ResourcesHelper(androidContext()) }
    single { SignatureHelper(androidContext()) }
}

private fun provideNetworkHelper(context: Context) = NetworkHelper(context)
private fun provideOkHttpClient(prefs: SharedPreferences): OkHttpClient {

    val interceptorData: Interceptor = Interceptor { chain ->
        var newRequest: Request = chain.request()

        newRequest = newRequest.newBuilder()
            .addHeader("Accept", "application/json")
            .addHeader("Content-Type", "application/json")
            .addHeader("X-Device-ID", prefs.getString(Constant.KEY_DEVICE_ID, "")!!)
            .addHeader("Authorization", "Bearer ".plus(prefs.getString(Constant.KEY_TOKEN, "")))
            .header("Connection", "close")
            .build()

        chain.proceed(newRequest)
    }

    return OkHttpClient.Builder()
        .addNetworkInterceptor(
            HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
        )
        .addInterceptor(interceptorData)
        .addNetworkInterceptor(ServerErrorInterceptor())
        .callTimeout(35, TimeUnit.SECONDS)
        .connectTimeout(35, TimeUnit.SECONDS)
        .readTimeout(35, TimeUnit.SECONDS)
        .writeTimeout(35, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
//           .followRedirects(false)
//           .followSslRedirects(false)
//           .certificatePinner(certificatePinner)
        .build()

}
val moshi: Moshi = Moshi.Builder()
    //.addLast(KotlinJsonAdapterFactory())
    .build()

private fun provideRetrofit(
    okHttpClient: OkHttpClient,
    BASE_URL: String
): Retrofit =
    Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .client(okHttpClient)
        .build()

private fun provideApiService(retrofit: Retrofit): ApiService =
    retrofit.create(ApiService::class.java)

private fun provideSharedPrefManager(): SharedPreferences = SharedPrefManager.initializePrefs()