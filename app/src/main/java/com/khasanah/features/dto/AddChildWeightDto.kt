package com.khasanah.features.dto
import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json


@JsonClass(generateAdapter = true)
data class AddChildWeightDto(
    @Json(name = "child")
    var child: String? = null,
    @Json(name = "weight")
    var weight: String? = null
)