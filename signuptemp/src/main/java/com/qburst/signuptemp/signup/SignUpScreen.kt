package com.qburst.signuptemp.signup

// SignUpScreen.kt
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.qburst.signuptemp.R
import com.qburst.signuptemp.fields

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel,
    fields: List<SignUpField>,
    isAuthenticated: () -> Unit,
    navigateToLogin: () -> Unit,
    content: (@Composable (
        signupUiSate: SignUpUiState.NotAuthenticated,
        signUpEvents: (SignUpUiEvent) -> Unit
    ) -> Unit)
){
    viewModel.setFieldValidator(fields)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when(uiState){
        SignUpUiState.Authenticated -> isAuthenticated()
        is SignUpUiState.NotAuthenticated ->{
            content(uiState as SignUpUiState.NotAuthenticated){ event ->
                when(event){
                    is SignUpUiEvent.FieldChanged -> viewModel.updateFieldValue(event.name, event.value)
                    SignUpUiEvent.NavigateToLogin -> navigateToLogin()
                    SignUpUiEvent.SignUp -> viewModel.signUp()
                }
            }
        }
    }
}

@Composable
fun SignUpScreen(
    @DrawableRes appIcon: Int,
    viewModel: SignUpViewModel,
    fields: List<SignUpField>,
    isAuthenticated: () -> Unit,
    navigateToLogin: () -> Unit,
){
    SignUpScreen(
        viewModel = viewModel,
        fields = fields,
        isAuthenticated = isAuthenticated,
        navigateToLogin = navigateToLogin,
    ){ uiState, signUpEvents ->
        SignUp(
            appIcon = appIcon,
            fields = fields,
            uiState = uiState,
            events = signUpEvents
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUp(
    modifier: Modifier = Modifier,
    @DrawableRes appIcon: Int,
    fields: List<SignUpField>,
    uiState: SignUpUiState.NotAuthenticated,
    events: (SignUpUiEvent) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()){
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
        ) {

            Icon(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 24.dp, bottom = 32.dp)
                    .size(128.dp),
                painter = painterResource(id = appIcon),
                contentDescription = "person",
            )

            fields.forEach { field ->
                val value = uiState.fieldValues[field.name] ?: ""
                val error = uiState.fieldErrors[field.name]
                TextField(
                    value = value,
                    onValueChange = { newValue ->
                        events(SignUpUiEvent.FieldChanged(field.name, newValue))
                    },
                    label = { Text(field.label) },
                    isError = error != null,
                    placeholder = { if (error != null) Text(error) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
            }
            Button(
                onClick = {
                    events(SignUpUiEvent.SignUp)
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 16.dp)
            ) {
                Text("Sign Up")
            }
        }

        if (uiState.isLoading){
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }

}

@Composable
@Preview
fun PreviewSignUpScreen() {
    SignUp(
        appIcon = R.drawable.baseline_person,
        fields = fields,
        uiState = SignUpUiState.NotAuthenticated()
    ){

    }
}
