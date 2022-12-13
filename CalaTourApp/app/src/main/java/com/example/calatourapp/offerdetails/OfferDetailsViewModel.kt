package com.example.calatourapp.offerdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.calatourapp.models.Offer
import com.example.calatourapp.repository.OffersRepository
import com.example.calatourapp.repository.OffersRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.*

class OfferDetailsViewModel(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val repository: OffersRepository = OffersRepositoryImpl

    private val _viewState = MutableStateFlow(OfferDetailsViewState())
    val viewState = _viewState.asStateFlow()

    init {
        val id = savedStateHandle.get<String>("offerId")

//        if (id != null) {
//
//        } else {
//
//        }
        id?.let {
            val offer = repository.getOfferById(UUID.fromString(it))
            _viewState.update { state ->
                state.copy(
                    offer = offer
                )
            }
        }
    }

    fun toggleIsFavorite() {
        val newOffer = repository.toggleIsFavorite(viewState.value.offer!!.id)
        _viewState.update {
            it.copy(
                offer = newOffer
            )
        }
    }
}

data class OfferDetailsViewState(
    val offer: Offer? = null
)