package com.qburst.signuplibs.signup

// SignUpViewModel.kt
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel

class SignUpViewModel(private val signUpRepository: SignUpRepository) : ViewModel() {

    val fieldValues = mutableStateMapOf<String, String>()
    val fieldErrors = mutableStateMapOf<String, String?>()
    private var fieldValidator: MutableMap<String, (String) -> String?> = mutableMapOf()

    fun setFieldValidator(fields: List<SignUpField>){
        if (fieldValues.isEmpty())
            fields.forEach { field ->
                fieldValues[field.name] = field.value
                fieldErrors[field.name] = null
                fieldValidator[field.name] = field.validator
            }
    }

    fun updateFieldValue(name: String, value: String){
        fieldValues[name] = value
        fieldErrors[name] = null
    }

    fun signUp() {
        fieldValues.forEach { (name, value) ->
            fieldErrors[name] = fieldValidator[name]?.invoke(value)
        }
        val isValidationFail = fieldErrors.values.any { it != null }
        if (!isValidationFail){
            signUpRepository.signUp(fieldValues.toMap())
        }
    }
}