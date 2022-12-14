package com.example.calatourapp.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calatourapp.repository.AuthenticationRepository
import com.example.calatourapp.repository.AuthenticationRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authenticationRepository: AuthenticationRepository = AuthenticationRepositoryImpl
) : ViewModel() {

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

    fun onLogin() = viewModelScope.launch {
        val username = viewState.value.username
        val usernameError = when {
//            username.isBlank() -> InputErrorType.Empty
//            username.length < 3 -> InputErrorType.TooShort
//            username != "admin" -> InputErrorType.Invalid
            else -> null
        }

        val password = viewState.value.password
        val passwordError = when {
//            password.isBlank() -> InputErrorType.Empty
//            password.length < 3 -> InputErrorType.TooShort
//            password != "password" -> InputErrorType.Invalid
            else -> null
        }

        when {
            /*
            usernameError != null && passwordError != null -> {
                _viewState.update {
                    it.copy(
                        action = LoginViewAction.ShowInputErrors(
                            usernameErrorType = usernameError,
                            passwordErrorType = passwordError
                        )
                    )
                }
            }

            usernameError != null -> {
                _viewState.update {
                    it.copy(
                        action = LoginViewAction.ShowInputErrors(
                            usernameErrorType = usernameError
                        )
                    )
                }
            }

            passwordError != null -> {
                _viewState.update {
                    it.copy(
                        action = LoginViewAction.ShowInputErrors(
                            passwordErrorType = passwordError
                        )
                    )
                }
            }
            */
            else -> {
                val isSuccessful = authenticationRepository.login(
                    username = username,
                    password = password
                )

                println("@@@@@@@@@@@@@@@@@@@@ $isSuccessful")

                _viewState.update {
                    it.copy(
                        action =
                            if (isSuccessful) LoginViewAction.LoggedIn
                            else LoginViewAction.ShowInputErrors(null, null)
                    )
                }
            }
        }
    }

    fun onGlobalLogout() = viewModelScope.launch {
        val username = viewState.value.username
        val password = viewState.value.password

        authenticationRepository.globalLogout(
            username = username,
            password = password
        )
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
