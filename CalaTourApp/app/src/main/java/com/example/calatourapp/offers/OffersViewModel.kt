package com.example.calatourapp.offers

import androidx.lifecycle.ViewModel
import com.example.calatourapp.models.Offer
import com.example.calatourapp.repository.OffersRepository
import com.example.calatourapp.repository.OffersRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID

class OffersViewModel(
    private val repository: OffersRepository = OffersRepositoryImpl
) : ViewModel() {

    private val _viewState = MutableStateFlow(
        OffersViewState(
            offers = repository.getOffers()
        )
    )
    val viewState = _viewState.asStateFlow()

    fun addOffer(index: Int) {
        val newOffer = Offer(
            name = "Cluj-Napoca",
            period = "3 nights",
            imageUrl = "https://i.imgur.com/D7LiBDn.png",
            price = 150.0,
            currency = "EUR",
            description = "description"
        )

        val newList = repository.addOffer(index, newOffer)
        _viewState.update { state ->
            state.copy(
                offers = newList
            )
        }
    }

    fun removeOfferById(id: UUID) {
        val newList = repository.removeOfferById(id)
        _viewState.update {
            it.copy(
                offers = newList
            )
        }
    }
}

data class OffersViewState(
    val offers: List<Offer>
)