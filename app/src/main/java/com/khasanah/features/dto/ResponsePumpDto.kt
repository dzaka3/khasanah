package com.khasanah.features.dto
import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json


@JsonClass(generateAdapter = true)
data class ResponsePumpDto(
    @Json(name = "data")
    var `data`: List<Data>? = null,
    @Json(name = "message")
    var message: Int? = null,
    @Json(name = "status")
    var status: String? = null
) {

    @JsonClass(generateAdapter = true)
    data class Data(
        @Json(name = "created_at")
        var createdAt: String? = null,
        @Json(name = "id")
        var id: Int? = null,
        @Json(name = "left")
        var left: Int? = null,
        @Json(name = "right")
        var right: Int? = null,
        @Json(name = "updated_at")
        var updatedAt: String? = null,
        @Json(name = "user")
        var user: Int? = null
    )
}