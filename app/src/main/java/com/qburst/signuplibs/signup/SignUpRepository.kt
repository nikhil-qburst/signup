package com.qburst.signuplibs.signup

import android.util.Log

// SignUpRepository.kt
class SignUpRepository {
    fun signUp(signUpData: Map<String, String>) {
        Log.d("signUp", "${signUpData.entries}")
    }
}
