package com.khasanah.features.dto
import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json


@JsonClass(generateAdapter = true)
data class ResponsePumpTodayDto(
    @Json(name = "data")
    var `data`: Data? = null,
    @Json(name = "message")
    var message: String? = null,
    @Json(name = "status")
    var status: String? = null
) {

    @JsonClass(generateAdapter = true)
    data class Data(
        @Json(name = "datepump")
        var datepump: String? = null,
        @Json(name = "pleft")
        var pleft: String? = null,
        @Json(name = "pright")
        var pright: String? = null,
        @Json(name = "total")
        var total: String? = null
    )
}