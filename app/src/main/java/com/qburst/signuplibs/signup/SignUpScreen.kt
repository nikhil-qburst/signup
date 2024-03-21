package com.qburst.signuplibs.signup

// SignUpScreen.kt
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.qburst.signuplibs.R
import com.qburst.signuplibs.fields
import com.qburst.signuplibs.validateEmail
import com.qburst.signuplibs.validatePassword
import com.qburst.signuplibs.validateUsername

@Composable
fun SignUpScreen(
    @DrawableRes appIcon: Int,
    viewModel: SignUpViewModel,
    fields: List<SignUpField>,
    isAuthenticated: () -> Unit,
    navigateToLogin: () -> Unit,
    content: (@Composable (
        fieldValues: SnapshotStateMap<String, String>,
        fieldErrors: SnapshotStateMap<String, String?>,
        signUpEvents: (SignUpUiEvent) -> Unit
    ) -> Unit)
){
    viewModel.setFieldValidator(fields)
    content(viewModel.fieldValues, viewModel.fieldErrors){ event ->
        when(event){
            is SignUpUiEvent.FieldChanged -> viewModel.updateFieldValue(event.name, event.value)
            SignUpUiEvent.NavigateToLogin -> navigateToLogin()
            SignUpUiEvent.SignUp -> viewModel.signUp()
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
        appIcon = appIcon,
        viewModel = viewModel,
        fields = fields,
        isAuthenticated = isAuthenticated,
        navigateToLogin = navigateToLogin,
    ){ fieldValues, fieldErrors, signUpEvents ->
        SignUp(
            appIcon = appIcon,
            fields = fields,
            fieldValues = fieldValues,
            fieldErrors = fieldErrors,
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
    fieldValues: SnapshotStateMap<String, String>,
    fieldErrors: SnapshotStateMap<String, String?>,
    events: (SignUpUiEvent) -> Unit
) {

//    viewModel.setFieldValidator(fields)
//    val fieldValues = viewModel.fieldValues
//    val fieldErrors = viewModel.fieldErrors

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
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
            val value = fieldValues[field.name] ?: ""
            val error = fieldErrors[field.name]
            TextField(
                value = value,
                onValueChange = { newValue ->
                    events(SignUpUiEvent.FieldChanged(field.name, newValue.trim()))
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
}

//@Composable
//@Preview
//fun PreviewSignUpScreen() {
//    SignUpScreen(
//        viewModel = SignUpViewModel(SignUpRepository()),
//        fields = fields,
//        appIcon = R.drawable.baseline_person
//    )
//}
