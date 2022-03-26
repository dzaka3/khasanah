package com.khasanah.features.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class ResponseStatusDto(
    @Json(name = "status")
    val status: String?,
    @Json(name = "message")
    val message: Any?,
    @Json(name = "data")
    val data: String? = null
)