package com.khasanah.features.dto
import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json

@JsonClass(generateAdapter = true)
data class UpdateProfileDto(
    @Json(name = "address")
    var address: String? = null,
    @Json(name = "age")
    var age: String? = null,
    @Json(name = "email")
    var email: String? = null,
    @Json(name = "name")
    var name: String? = null,
    @Json(name = "phone")
    var phone: String? = null
)