package com.example.calatourapp.models

import java.util.UUID

data class Offer(
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val period: String,
    val imageUrl: String,
    val price: Double,
    val currency: String,
    val description: String
)
