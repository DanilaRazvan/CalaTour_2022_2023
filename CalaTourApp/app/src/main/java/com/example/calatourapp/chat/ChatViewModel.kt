package com.example.calatourapp.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calatourapp.api.data.ChatMessage
import com.example.calatourapp.repository.AuthenticationRepository
import com.example.calatourapp.repository.AuthenticationRepositoryImpl
import com.example.calatourapp.repository.ChatRepository
import com.example.calatourapp.repository.ChatRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatViewModel(
    private val authRepo: AuthenticationRepository = AuthenticationRepositoryImpl,
    private val chatRepository: ChatRepository = ChatRepositoryImpl
) : ViewModel() {

    private val _viewState = MutableStateFlow(ChatViewState())
    val viewState = _viewState.asStateFlow()

    init {
        _viewState.update {
            it.copy(
                user = authRepo.displayName
            )
        }
    }

    fun onMessageChanged(message: String) {
        _viewState.update {
            it.copy(
                message = message
            )
        }
    }

    fun onSendMessage() = viewModelScope.launch {
        val newList = chatRepository.sendMessage(viewState.value.message)

        _viewState.update {
            it.copy(
                messages = newList
            )
        }
    }

    fun onReadMessages() {
        viewModelScope.launch {
            val newList = chatRepository.readMessages()
            println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ Messages: $newList")

            _viewState.update {
                it.copy(
                    messages = newList
                )
            }
        }
    }
}

data class ChatViewState(
    val user: String = "",
    val message: String = "",
    val messages: List<ChatMessage> = emptyList()
)