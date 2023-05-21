package com.disfluency.screens.pacientes

import android.app.DatePickerDialog
import android.text.InputType
import android.widget.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardCapitalization
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Collections
import java.util.function.Predicate

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
fun inputDate(label: String, startDate: LocalDate): Input<LocalDate>{
    var dateValue by remember { mutableStateOf(startDate) }
    var showedDateValue by remember { mutableStateOf("")}

    var input: Input<LocalDate>? = null //TODO: Meter dentro de una caja

    val datePicker = DatePickerDialog(LocalContext.current,
        { _: DatePicker, year, month, day ->
            dateValue = LocalDate.of(year, month, day)
            showedDateValue = dateValue.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            //Aca se deberia ejecutar el onChange, pero no lo hace.
            input?.validate()
            //Por algun motivo, adentro del validate se analiza el valor anterior
        }
        , dateValue.year, dateValue.monthValue, dateValue.dayOfMonth
    )

    input = input(
        label = label
        , getRealValue = {dateValue}
        , formattedValue = {showedDateValue}, onValueChange = {}
        , enabled = false
        , modifier = Modifier.clickable { datePicker.show() }
        , trailingIcon = {DateIcon()}
    )

    return input
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
    Icon(Icons.Filled.Info, "Error", tint = MaterialTheme.colorScheme.error)
}

@Composable
fun DateIcon(){
    Icon(Icons.Filled.CalendarToday, "Fecha", tint = MaterialTheme.colorScheme.primary)
}