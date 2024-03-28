package com.qburst.signuptemp.signup

import android.util.Log
import kotlinx.coroutines.delay

// SignUpRepository.kt
class SignUpRepository {
    suspend fun signUp(signUpData: Map<String, String>): Boolean {
        Log.d("signUp", "${signUpData.entries}")
        delay(11000)
        return true
    }
}
