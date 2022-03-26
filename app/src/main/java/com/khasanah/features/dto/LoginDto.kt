package com.khasanah.features.dto
import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json

@JsonClass(generateAdapter = true)
data class LoginDto(
    @Json(name = "password")
    var password: String? = null,
    @Json(name = "phone")
    var phone: String? = null
)