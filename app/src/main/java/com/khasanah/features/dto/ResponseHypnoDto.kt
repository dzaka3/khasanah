package com.khasanah.features.dto
import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json


@JsonClass(generateAdapter = true)
data class ResponseHypnoDto(
    @Json(name = "data")
    var `data`: List<Data>? = null,
    @Json(name = "message")
    var message: String? = null,
    @Json(name = "status")
    var status: String? = null
) {

    @JsonClass(generateAdapter = true)
    data class Data(
        @Json(name = "created_at")
        var createdAt: String? = null,
        @Json(name = "description")
        var description: String? = null,
        @Json(name = "extension")
        var extension: String? = null,
        @Json(name = "file")
        var `file`: String? = null,
        @Json(name = "file_type")
        var fileType: String? = null,
        @Json(name = "id")
        var id: String? = null,
        @Json(name = "name")
        var name: String? = null,
        @Json(name = "size")
        var size: String? = null,
        @Json(name = "updated_at")
        var updatedAt: String? = null,

        var nav : Int = 0
    )
}