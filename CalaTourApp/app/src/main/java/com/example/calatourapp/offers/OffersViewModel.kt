package com.example.calatourapp.offers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calatourapp.models.Offer
import com.example.calatourapp.repository.OffersRepository
import com.example.calatourapp.repository.OffersRepositoryImpl
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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

    init {
        viewModelScope.launch {

            _viewState.update {
                it.copy(
                    isLoading = true
                )
            }

            delay(3000L)

            _viewState.update {
                it.copy(
                    isLoading = false
                )
            }
        }

    }

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

    fun resetList() {
        val newList = repository.resetList()
        _viewState.update {
            it.copy(
                offers = newList
            )
        }
    }
}

data class OffersViewState(
    val offers: List<Offer>,
    val isLoading: Boolean = false
)