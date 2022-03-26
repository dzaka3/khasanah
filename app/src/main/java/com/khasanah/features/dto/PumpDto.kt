package com.khasanah.features.dto
import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json


@JsonClass(generateAdapter = true)
data class PumpDto(
    @Json(name = "left")
    var left: String? = null,
    @Json(name = "right")
    var right: String?  = null
)