package com.disfluency.components.inputs

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.*
import java.util.*

@Composable
fun inputAsString(label: String, validations: List<(String)->Boolean> = Collections.emptyList(), keyboardOptions: KeyboardOptions = KeyboardOptions.Default): Input<String> {
    var value by remember { mutableStateOf("") }

    return input(
        formattedValue = {value},
        getRealValue = {value},
        onValueChange = {value = it},
        label = label,
        keyboardOptions = keyboardOptions,
        validations = validations
    )
}