package com.example.calatourapp.repository

import com.example.calatourapp.models.Offer
import java.util.UUID

interface OffersRepository {
    fun getOffers() : List<Offer>

    fun getOfferById(id: UUID): Offer?

    fun addOffer(index: Int = 0, offer: Offer): List<Offer>

    fun removeOfferById(id: UUID): List<Offer>

    fun resetList(): List<Offer>
}

object OffersRepositoryImpl: OffersRepository {

    private val initialOffers = listOf(
        Offer(
            name = "Barcelona",
            period = "3 nights",
            imageUrl = "https://i.imgur.com/D7LiBDn.png",
            price = 300.0,
            currency = "EUR",
            description = "Barcelona has many venues for live music and theatre, including the world-renowned Gran Teatre del Liceu opera."
        ),
        Offer(
            name = "Maldive",
            period = "7 nights",
            imageUrl = "https://i.imgur.com/5umpI6K.png",
            price = 1050.0,
            currency = "EUR",
            description = "The first maldivians did not leave any archaeological artifacts."
        ),
        Offer(
            name = "Thailand",
            period = "10 nights",
            imageUrl = "https://i.imgur.com/q0EeSYx.png",
            price = 1250.0,
            currency = "EUR",
            description = "The Andaman Sea is a precious natural resource as it hosts the most popular and luxurious resorts in Asia."
        )
    )

    private val offers = initialOffers.toMutableList()

    override fun getOffers(): List<Offer> = offers.toList()

    override fun getOfferById(id: UUID): Offer? =
        offers.firstOrNull {
            it.id == id
        }

    override fun addOffer(index: Int, offer: Offer): List<Offer> =
        offers.apply {
            add(index, offer)
        }.toList()

    override fun removeOfferById(id: UUID): List<Offer> =
        offers.apply {
            removeIf { it.id == id }
        }.toList()

    override fun resetList(): List<Offer> =
        offers.apply {
            clear()
            addAll(initialOffers)
        }.toList()
}