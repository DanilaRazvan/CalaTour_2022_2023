package com.example.calatourapp.api.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginResponse(
    @Json(name = "id")
    val id: Int,
    @Json(name = "token")
    val token: String,
    @Json(name = "display")
    val displayName: String
)

@JsonClass(generateAdapter = true)
data class ReadMessagesResponse(
    @Json(name = "messages")
    val messages: List<ChatMessage>
)

@JsonClass(generateAdapter = true)
data class ChatMessage(
    @Json(name = "sender")
    val sender: String,
    @Json(name = "text")
    val text: String,
    @Json(name = "timestamp")
    val timestamp: String
)