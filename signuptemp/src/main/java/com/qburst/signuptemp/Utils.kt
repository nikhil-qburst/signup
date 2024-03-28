package com.qburst.signuptemp

import android.util.Patterns
import androidx.compose.ui.text.input.KeyboardType


val validateUsername: (String) -> String? = {
         if (it.isBlank()) {
            "Username cannot be empty"
        } else {
            null
        }
    }

    val validateEmail: (String) -> String? = {
        if (Patterns.EMAIL_ADDRESS.matcher(it).matches()) {
            null
        } else {
            "Invalid email address"
        }
    }

    val validatePassword: (String) -> String? = {
        if (it.length >= 8) {
            null
        } else {
            "Password must be at least 8 characters"
        }
    }

val fields = listOf(
    com.qburst.signuptemp.signup.SignUpField("username", "Username", validator = validateUsername),
    com.qburst.signuptemp.signup.SignUpField("email", "Email", validator = validateEmail),
    com.qburst.signuptemp.signup.SignUpField(
        "password",
        "Password",
        KeyboardType.Password,
        validatePassword
    )
)
