package com.qburst.signuptemp.signup

sealed class SignUpUiState {
    data object Authenticated : SignUpUiState()
    data class NotAuthenticated(
        val fieldValues: Map<String, String> = mapOf(),
        val fieldErrors: Map<String, String?> = mapOf(),
        val error: String? = null,
        val isLoading: Boolean = false,
    ) : SignUpUiState()
}
