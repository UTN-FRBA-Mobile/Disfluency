package com.disfluency.components.inputs

import android.util.Patterns
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.core.text.isDigitsOnly

interface InputValidation {
    fun validate(input: String): Boolean
}

class MandatoryValidation: InputValidation {
    override fun validate(input: String): Boolean {
        return input.isNotEmpty() && input.isNotBlank()
    }
}

class EmailValidation: InputValidation {
    override fun validate(input: String): Boolean {
        return Patterns.EMAIL_ADDRESS.asPredicate().test(input)
    }
}

class DigitsOnlyValidation: InputValidation {
    override fun validate(input: String): Boolean {
        return input.isDigitsOnly()
    }
}

@Composable
fun ValidatedTextInput(
    state: MutableState<String>,
    label: String,
    keyboardOptions: KeyboardOptions,
    validations: List<InputValidation>
){
    var wrongValue: Boolean by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = state.value,
        onValueChange = {
            state.value = it
            wrongValue = !validations.all { v -> v.validate(it) }
        },
        label = { Text(label) },
        singleLine = true,
        isError = wrongValue,
        trailingIcon = { if (wrongValue) Icon(Icons.Filled.Info, "Error") },
        keyboardOptions = keyboardOptions,
        supportingText = {
            if(wrongValue)
                if(state.value.isBlank()) Text(text = "Este campo es requerido")
                else Text("Ingrese un $label válido")
        }
    )
}

@Composable
fun MandatoryTextInput(state: MutableState<String>, label: String){
    ValidatedTextInput(
        state = state,
        label = label,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next, capitalization = KeyboardCapitalization.Words),
        validations = listOf(MandatoryValidation())
    )
}

@Composable
fun MandatoryDigitsInput(state: MutableState<String>, label: String){
    ValidatedTextInput(
        state = state,
        label = label,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next, keyboardType = KeyboardType.NumberPassword),
        validations = listOf(MandatoryValidation(), DigitsOnlyValidation())
    )
}

@Composable
fun MandatoryEmailInput(state: MutableState<String>, label: String){
    ValidatedTextInput(
        state = state,
        label = label,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Done),
        validations = listOf(MandatoryValidation(), EmailValidation())
    )
}