package com.khasanah.features.dto
import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json


@JsonClass(generateAdapter = true)
data class ResponsePumpStatistic(
    @Json(name = "data")
    var `data`: List<Data>? = null,
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
        var pleft: Float? = 0F,
        @Json(name = "pright")
        var pright: Float? = 0F,
        @Json(name = "total")
        var total: Float? = 0F
    )
}