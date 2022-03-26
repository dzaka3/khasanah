package com.khasanah.features.dto
import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json


@JsonClass(generateAdapter = true)
data class ResponseChildDto(
    @Json(name = "data")
    var data: List<Data>? = null,
    @Json(name = "message")
    var message: String? = null,
    @Json(name = "status")
    var status: String? = null
) {

    @JsonClass(generateAdapter = true)
    data class Data(
        @Json(name = "birthdate")
        var birthdate: String? = null,
        @Json(name = "created_at")
        var createdAt: String? = null,
        @Json(name = "gender")
        var gender: String? = null,
        @Json(name = "id")
        var id: String? = "1",
        @Json(name = "mother")
        var mother: String? = null,
        @Json(name = "name")
        var name: String? = null,
        @Json(name = "updated_at")
        var updatedAt: String? = null,

        var nav : Int = 0
    )
}