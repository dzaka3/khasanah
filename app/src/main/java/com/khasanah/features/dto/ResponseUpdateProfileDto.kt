package com.khasanah.features.dto
import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json


@JsonClass(generateAdapter = true)
data class ResponseUpdateProfileDto(
    @Json(name = "data")
    var data: Data?,
    @Json(name = "message")
    var message: String?,
    @Json(name = "status")
    var status: String?
) {

    @JsonClass(generateAdapter = true)
    data class Data(
        @Json(name = "token")
        var token: String?,
        @Json(name = "type")
        var type: String?
    )
}