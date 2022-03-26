package com.khasanah.features.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TheoryDto(
    @Json(name = "data")
    var data: List<Data>? = null,
    @Json(name = "message")
    var message: String? = null,
    @Json(name = "status")
    var status: String? = null
) {
    @JsonClass(generateAdapter = true)
    data class Data(
        @Json(name = "id")
        var id: String? = null,
        @Json(name = "name")
        var name: String? = null,

        var nav: Int? = null
        )
}
