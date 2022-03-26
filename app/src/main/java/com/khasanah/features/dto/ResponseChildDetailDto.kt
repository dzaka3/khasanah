package com.khasanah.features.dto
import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json


@JsonClass(generateAdapter = true)
data class ResponseChildDetailDto(
    @Json(name = "data")
    var `data`: Data? = null,
    @Json(name = "message")
    var message: Int? = null,
    @Json(name = "status")
    var status: String? = null
) {

    @JsonClass(generateAdapter = true)
    data class Data(
        @Json(name = "child")
        var child: Child? = null,
        @Json(name = "weight")
        var weight: List<Weight>? = null
    )

    @JsonClass(generateAdapter = true)
    data class Child(
        @Json(name = "birthdate")
        var birthdate: String? = null,
        @Json(name = "created_at")
        var createdAt: String? = null,
        @Json(name = "gender")
        var gender: String? = null,
        @Json(name = "id")
        var id: Int? = null,
        @Json(name = "mother")
        var mother: Int? = null,
        @Json(name = "name")
        var name: String? = null,
        @Json(name = "updated_at")
        var updatedAt: String? = null
    )

    @JsonClass(generateAdapter = true)
    data class Weight(
        @Json(name = "created_at")
        var createdAt: String? = null,
        @Json(name = "id")
        var id: Float? = 0F,
        @Json(name = "weight")
        var weight: Float? = 0F
    )
}