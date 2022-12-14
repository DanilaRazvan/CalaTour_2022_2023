package com.example.calatourapp.api

import com.example.calatourapp.api.data.LoginRequest
import com.example.calatourapp.api.data.LoginResponse
import com.example.calatourapp.api.data.ReadMessagesResponse
import com.example.calatourapp.api.data.SendMessageRequest
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

const val contentType = "Content-Type: application/json"

interface PiuApi {

    @Headers(contentType)
    @POST("authenticate.php")
    suspend fun authenticate(
        @Body body: LoginRequest
    ) : LoginResponse

    @Headers(contentType)
    @DELETE("logout.php")
    suspend fun logout()

    @Headers(contentType)
    @HTTP(method = "DELETE", path = "logout.php", hasBody = true)
    suspend fun globalLogout(
        @Body body: LoginRequest
    )

    @Headers(contentType)
    @PUT("sendmessage.php")
    suspend fun sendMessage(
        @Header("Authorization") token: String,
        @Body body: SendMessageRequest
    )

    @Headers(contentType)
    @GET("readmessages.php")
    suspend fun readMessages(
        @Header("Authorization") token: String
    ): ReadMessagesResponse


    companion object {
        private val logger = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        private val httpClient = OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()

        fun create(): PiuApi = Retrofit.Builder()
            .baseUrl("http://193.226.17.35/chat-piu/")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(httpClient)
            .build()
            .create(PiuApi::class.java)
    }
}