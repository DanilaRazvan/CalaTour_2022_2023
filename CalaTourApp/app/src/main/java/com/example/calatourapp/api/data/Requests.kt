package com.example.calatourapp.api.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginRequest(
    @Json(name = "username")
    val username: String,

    @Json(name = "password")
    val password: String
)

@JsonClass(generateAdapter = true)
data class SendMessageRequest(
    @Json(name = "message")
    val message: String
)