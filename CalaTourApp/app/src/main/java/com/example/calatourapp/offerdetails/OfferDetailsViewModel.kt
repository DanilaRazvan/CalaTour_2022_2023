package com.example.calatourapp.offerdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.calatourapp.models.Offer
import com.example.calatourapp.repository.OffersRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.*

class OfferDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: OffersRepository
) : ViewModel() {

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
}

data class OfferDetailsViewState(
    val offer: Offer? = null
)