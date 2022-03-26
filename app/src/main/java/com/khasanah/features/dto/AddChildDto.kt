package com.khasanah.features.dto
import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json


@JsonClass(generateAdapter = true)
data class AddChildDto(
    @Json(name = "birthdate")
    var birthdate: String? = null,
    @Json(name = "gender")
    var gender: String? = null,
    @Json(name = "name")
    var name: String? = null
)