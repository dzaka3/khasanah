package com.khasanah.features.dto

import com.khasanah.features.di.moshi
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
class SharedPrefsModel : Serializable {
    @Json(name = "token")
    var token: String? = null

    companion object {
        fun create(serializedData: String?): SharedPrefsModel? {
            // Use GSON to instantiate this class using the JSON representation of the state
            //https://stackoverflow.com/questions/4147012/can-you-avoid-gson-converting-and-into-unicode-escape-sequences

            return moshi.adapter(SharedPrefsModel::class.java).fromJson(serializedData)
        }
    }
}
