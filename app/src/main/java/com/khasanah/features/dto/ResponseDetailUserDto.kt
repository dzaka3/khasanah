package com.khasanah.features.dto
import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json


@JsonClass(generateAdapter = true)
data class ResponseDetailUserDto(
    @Json(name = "data")
    var `data`: Data? = null,
    @Json(name = "message")
    var message: String? = null,
    @Json(name = "status")
    var status: String? = null
) {
    @JsonClass(generateAdapter = true)
    data class Data(
        @Json(name = "address")
        var address: String? = null,
        @Json(name = "age")
        var age: String? = null,
        @Json(name = "childs")
        var childs: String? = null,
        @Json(name = "compString")
        var compString: String? = null,
        @Json(name = "country")
        var country: String? = null,
        @Json(name = "created_at")
        var createdAt: String? = null,
        @Json(name = "email")
        var email: String? = null,
        @Json(name = "email_verified_at")
        var emailVerifiedAt: String? = null,
        @Json(name = "id")
        var id: Int? = null,
        @Json(name = "is_active")
        var isActive: Int? = null,
        @Json(name = "is_admin")
        var isAdmin: Int? = null,
        @Json(name = "is_login")
        var isLogin: Int? = null,
        @Json(name = "language")
        var language: String? = null,
        @Json(name = "last_login")
        var lastLogin: String? = null,
        @Json(name = "linkedin")
        var linkedin: String? = null,
        @Json(name = "name")
        var name: String? = null,
        @Json(name = "phone")
        var phone: String? = null,
        @Json(name = "updated_at")
        var updatedAt: String? = null
    )
}