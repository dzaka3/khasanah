package com.khasanah.features.dto
import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json


@JsonClass(generateAdapter = true)
data class RegisterDto(
    @Json(name = "c_password")
    var cPassword: String? = null,
    @Json(name = "name")
    var name: String? = null,
    @Json(name = "email")
    var email: String? = null,
    @Json(name = "password")
    var password: String? = null,
    @Json(name = "phone")
    var phone: String? = null
)