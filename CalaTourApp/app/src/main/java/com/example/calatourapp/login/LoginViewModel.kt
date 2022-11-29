package com.example.calatourapp.login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel : ViewModel() {

    private val initialState = LoginViewState()
    private val _viewState = MutableStateFlow(initialState)
    val viewState = _viewState.asStateFlow()

    fun onUsernameChanged(newUsername: String) {
        _viewState.update { state ->
            state.copy(
                username = newUsername,
                action = null
            )
        }
    }

    fun onPasswordChanged(newUsername: String) {
        _viewState.update { state ->
            state.copy(
                password = newUsername,
                action = null
            )
        }
    }

    fun onLogin() {
        val username = _viewState.value.username
        val password = _viewState.value.password

        _viewState.update { state ->
            state.copy(
                action = LoginViewAction.LoggedIn
            )
        }
    }
}

data class LoginViewState(
    val username: String = "",
    val password: String = "",
    val action: LoginViewAction? = null
)


sealed class LoginViewAction {
    object LoggedIn: LoginViewAction()

    data class ShowInputErrors(
        val usernameError: InputErrorType? = null,
        val passwordError: InputErrorType? = null
    ): LoginViewAction()
}

sealed class InputErrorType {
    object Empty: InputErrorType()
    object TooShort: InputErrorType()
    object Invalid: InputErrorType()
}
