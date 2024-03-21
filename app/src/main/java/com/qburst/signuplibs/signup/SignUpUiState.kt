package com.qburst.signuplibs.signup

sealed class SignUpUiState {
    data object Authenticated : SignUpUiState()
    data object Loading: SignUpUiState()
    data class NotAuthenticated(
        val fieldValues: Map<String, String> = mapOf(),
        val fieldErrors: Map<String, String?> = mapOf(),
        val error: String? = null
    ) : SignUpUiState()
}
