package com.example.calatourapp.repository

import com.example.calatourapp.api.PiuApi
import com.example.calatourapp.api.data.ChatMessage
import com.example.calatourapp.api.data.SendMessageRequest
import java.time.LocalDateTime

interface ChatRepository {
    suspend fun readMessages() : List<ChatMessage>
    suspend fun sendMessage(message: String): List<ChatMessage>
}

object ChatRepositoryImpl: ChatRepository {
    private val authenticationRepository: AuthenticationRepository = AuthenticationRepositoryImpl
    private val piuApi = PiuApi.create()

    private val messages = mutableListOf<ChatMessage>()

    override suspend fun readMessages(): List<ChatMessage> {
        return try {
            val remoteMessages = piuApi.readMessages(
                "Bearer: " + authenticationRepository.token
            )
            messages.apply {
                addAll(remoteMessages.messages)
            }.toList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun sendMessage(message: String): List<ChatMessage> {
        return try {
            val newMessage = ChatMessage(
                sender = authenticationRepository.displayName,
                text = message,
                timestamp = LocalDateTime.now().toString()
            )

            piuApi.sendMessage(
                token = "Bearer: " + authenticationRepository.token,
                body = SendMessageRequest(message),
            )

            messages.apply {
                add(newMessage)
            }.toList()
        } catch (e: Exception) {
            emptyList()
        }
    }
}