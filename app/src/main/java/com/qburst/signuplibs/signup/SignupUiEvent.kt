package com.qburst.signuplibs.signup

sealed class SignUpUiEvent {
    data class FieldChanged(val name: String, val value: String) : SignUpUiEvent()
    data object SignUp : SignUpUiEvent()
    data object NavigateToLogin : SignUpUiEvent()
}
