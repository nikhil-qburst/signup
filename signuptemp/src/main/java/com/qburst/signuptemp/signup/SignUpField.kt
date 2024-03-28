package com.qburst.signuptemp.signup

import androidx.compose.ui.text.input.KeyboardType

data class SignUpField(
    val name: String,
    val label: String,
    val inputType: KeyboardType = KeyboardType.Text,
    val validator: (String) -> String? = {null},
    val value: String = "",
)