package com.qburst.signuplibs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.qburst.signuptemp.signup.SignUpRepository
import com.qburst.signuptemp.signup.SignUpScreen
import com.qburst.signuptemp.signup.SignUpViewModel
import com.qburst.signuplibs.ui.theme.SignUpLibsTheme
import com.qburst.signuptemp.fields

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SignUpLibsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SignUpScreen(
                        appIcon = R.drawable.baseline_person,
                        viewModel = SignUpViewModel(SignUpRepository()),
                        fields = fields,
                        isAuthenticated = {},
                        navigateToLogin = {}
                    )
                }
            }
        }
    }
}

