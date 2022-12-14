package com.example.calatourapp.repository

import com.example.calatourapp.api.PiuApi
import com.example.calatourapp.api.data.LoginRequest

interface AuthenticationRepository {
    var token: String?
    var displayName: String

    suspend fun login(
        username: String,
        password: String
    ): Boolean

    suspend fun logout(): Boolean
    suspend fun globalLogout(
        username: String,
        password: String
    ): Boolean
}

object AuthenticationRepositoryImpl: AuthenticationRepository {

    private val piuApi = PiuApi.create()

    override var token: String? = null
    override var displayName: String = ""

    override suspend fun login(username: String, password: String): Boolean {
        return try {
            val response = piuApi.authenticate(
                LoginRequest(username, password)
            )

            token = response.token
            displayName = response.displayName

            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun logout(): Boolean {
        return try {
            piuApi.logout()

            token = null
            displayName = ""

            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun globalLogout(username: String, password: String): Boolean {
        return try {
            piuApi.globalLogout(
                LoginRequest(username, password)
            )

            token = null
            displayName = ""
            true
        } catch (e: Exception) {
            false
        }
    }
}
