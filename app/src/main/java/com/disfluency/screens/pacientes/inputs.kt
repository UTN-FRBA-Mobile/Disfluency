package com.disfluency.screens.pacientes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Collections

data class Input<T>(val value: T, val wrongValue: ()->Boolean, val validate: (T)->Unit){
    fun validate() {
        validate(value)
    }
}

@Composable
fun inputString(label: String, validations: List<(String)->Boolean> = Collections.emptyList(), keyboardOptions: KeyboardOptions = KeyboardOptions.Default): Input<String>{
    var value by remember { mutableStateOf("") }

    return input(formattedValue = {value}
        , getRealValue = {value}
        , onValueChange = {value = it}
        , enabled = true
        , label = label
        , keyboardOptions = keyboardOptions
        , validations = validations
    )
}

@Composable
fun inputDate(label: String): Input<LocalDate?>{
    var dateValue: LocalDate? by remember {
        mutableStateOf(null)
    }
    var formattedValue by rememberSaveable {
        mutableStateOf("")
    }

    val datePickerState = rememberDatePickerState(initialDisplayMode = DisplayMode.Input)
    var openDialog by remember {mutableStateOf(false)}

    /* * * * */
    val disableDialog = {openDialog = false}
    var input: Input<LocalDate?>? = null

    Box {
        input = input(
            label = label
            , getRealValue = {dateValue}
            , formattedValue = {formattedValue}, onValueChange = {}
            , trailingIcon = {DateIcon()}
        )

        Box(modifier = Modifier
            .matchParentSize()
            .clickable { openDialog = true })
    }

    if (openDialog) {
        DatePickerDialog(
            onDismissRequest = disableDialog,
            confirmButton = {
                TextButton(
                    onClick = {
                        dateValue = millisecondsToLocalDate(datePickerState.selectedDateMillis!!)
                        formattedValue = dateValue!!.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"))
                        input?.validate()
                        disableDialog()
                    }
                    , enabled = datePickerState.selectedDateMillis!=null
                ) {
                    Text("Ok")
                }
            },
            dismissButton = {
                TextButton(disableDialog) {
                    Text("Cancelar")
                }
            },
            content = {DatePicker(state = datePickerState, title = null, headline = null, showModeToggle = true)}
        )
    }

    return input!!
}

fun millisecondsToLocalDate(milliseconds: Long): LocalDate{
    return Instant.ofEpochMilli(milliseconds)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
}

@Composable
fun <T> input(
    formattedValue: ()->String, getRealValue: ()->T
    , enabled: Boolean = true
    , onValueChange: (String)->Unit
    , modifier: Modifier = Modifier
    , trailingIcon: @Composable ()->Unit={}
    , label: String
    , validations: List<((T) -> Boolean)> = listOf()
    , keyboardOptions: KeyboardOptions = KeyboardOptions.Default
): Input<T>{

    val valueAsString = formattedValue()
    var wrongValue: Boolean by remember { mutableStateOf(false) }
    val validate: (T)->Unit = {value->
        wrongValue = formattedValue().isBlank() || validations.any{!it(value)}
    }

    OutlinedTextField(
        value = valueAsString
        , onValueChange = {
            onValueChange(it)
            validate(getRealValue())
        }
        , label = { Text(label) }
        , singleLine = true
        , isError = wrongValue
        , trailingIcon = {if (wrongValue) ErrorIcon() else trailingIcon()}
        , keyboardOptions = keyboardOptions
        , enabled = enabled
        , modifier = modifier
    )

    return Input(getRealValue(), {wrongValue}, validate)
}

@Composable
fun ErrorIcon(){
    Icon(Icons.Filled.Info, "Error")
}

@Composable
fun DateIcon(){
    Icon(Icons.Filled.CalendarToday, "Fecha", tint = MaterialTheme.colorScheme.primary)
}