package com.qburst.signuptemp.signup

// SignUpViewModel.kt
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignUpViewModel(private val signUpRepository: SignUpRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<SignUpUiState> = MutableStateFlow(SignUpUiState.NotAuthenticated())
    val uiState = _uiState.asStateFlow()

    private val currentUiData: SignUpUiState.NotAuthenticated?
        get() = if (_uiState.value is SignUpUiState.NotAuthenticated)
            _uiState.value as SignUpUiState.NotAuthenticated
        else null

    private var fieldValidator: MutableMap<String, (String) -> String?> = mutableMapOf()

    fun setFieldValidator(fields: List<SignUpField>){
        if (fieldValidator.isEmpty()) {
            val fieldValues = mutableStateMapOf<String, String>()
            val fieldErrors = mutableStateMapOf<String, String?>()
            fields.forEach { field ->
                fieldValues[field.name] = field.value
                fieldErrors[field.name] = null
                fieldValidator[field.name] = field.validator
            }

            currentUiData?.let {
                _uiState.tryEmit(
                    it.copy(
                        fieldValues = fieldValues,
                        fieldErrors = fieldErrors
                    )
                )
            }
        }
    }

    fun updateFieldValue(name: String, value: String){
        currentUiData?.let { data ->
            _uiState.tryEmit(
                data.copy(
                    fieldValues = data.fieldValues.mapValues { if (it.key == name) value else it.value },
                    fieldErrors = data.fieldErrors.mapValues { if (it.key == name) null else it.value }
                )
            )
        }
    }

    fun signUp() = viewModelScope.launch {
        currentUiData?.let {data ->
            val tempData = data.copy(
                fieldErrors = data.fieldErrors.mapValues {
                    data.fieldValues[it.key]?.let { fieldValues ->
                        fieldValidator[it.key]?.invoke(
                            fieldValues
                        )
                    }
                }
            )
            val isValidationFail = tempData.fieldErrors.values.any { it != null }
            _uiState.emit(tempData.copy(isLoading = !isValidationFail))
            if (!isValidationFail){
                if (signUpRepository.signUp(data.fieldValues.mapValues { it.value.trim() })){
                    _uiState.emit(tempData.copy(isLoading = false))
                }
            }
        }
    }
}