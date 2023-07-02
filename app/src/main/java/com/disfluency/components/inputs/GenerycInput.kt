package com.disfluency.components.inputs

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

data class Input<T>(val value: T, val wrongValue: ()->Boolean, val validate: (T)->Unit){
    fun validate() {
        validate(value)
    }
}

@Composable
fun <T> input(
    formattedValue: ()->String, getRealValue: ()->T,
    enabled: Boolean = true,
    onValueChange: (String)->Unit, modifier: Modifier = Modifier,
    trailingIcon: @Composable ()->Unit={},
    label: String,
    validations: List<((T) -> Boolean)> = listOf(),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
): Input<T> {

    val valueAsString = formattedValue()
    var wrongValue: Boolean by remember { mutableStateOf(false) }
    val validate: (T)->Unit = {value->
        wrongValue = formattedValue().isBlank() || validations.any{!it(value)}
    }

    OutlinedTextField(
        value = valueAsString,
        onValueChange = {
            onValueChange(it)
            validate(getRealValue())
        },
        label = { Text(label) },
        singleLine = true,
        isError = wrongValue,
        trailingIcon = {if (wrongValue) ErrorIcon() else trailingIcon()},
        keyboardOptions = keyboardOptions,
        enabled = enabled,
        modifier = modifier,
        supportingText = {
            if(wrongValue)
                if(formattedValue().isBlank()) Text(text = "Este campo es requerido")
                else Text("Ingrese un $label válido")
        }
    )

    return Input(getRealValue(), {wrongValue}, validate)
}

@Composable
fun ErrorIcon(){
    Icon(Icons.Filled.Info, "Error")
}