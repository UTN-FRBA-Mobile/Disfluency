package com.disfluency.screens.pacientes

import android.app.DatePickerDialog
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
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class Value<T>(val value: T, val wrongValue: ()->Boolean, val validate: (String)->Unit, val showedValue: () -> String){
    fun validate() {
        validate(showedValue())
    }
}

@Composable
fun inputString(label: String, valid: (String)->Boolean, keyboardOptions: KeyboardOptions = KeyboardOptions.Default): Value<String>{
    var value by remember { mutableStateOf("") }
    return inputStr(
        showedValue = {value}
        , onValueChange = { value = it }
        , label = label
        , keyboardOptions = keyboardOptions
        , valid = valid
    )
}

@Composable
fun ErrorIcon(){
    Icon(Icons.Filled.Info, "Error", tint = MaterialTheme.colorScheme.error)
}

@Composable
fun inputDate(label: String): Value<LocalDate>{
    var dateValue by remember { mutableStateOf(LocalDate.now()) }
    var showedDateValue by remember { mutableStateOf("")}

    var value: Value<LocalDate>? = null //TODO: Meter dentro de una caja

    val datePicker = DatePickerDialog(LocalContext.current,
        { _: DatePicker, year, month, day ->
            dateValue = LocalDate.of(year, month, day)
            showedDateValue = dateValue.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            //Aca se deberia ejecutar el onChange, pero no lo hace.
            value?.validate()
            //Por algun motivo, adentro del validate se analiza el valor anterior
        }
        , dateValue.year, dateValue.monthValue, dateValue.dayOfMonth
    )

    value = input(
        label = label
        , realValue = dateValue
        , showedValue = {showedDateValue}, onValueChange = {}
        , enabled = false
        , modifier = Modifier.clickable { datePicker.show() }
        , trailingIcon = {DateIcon()}
        , valid = String::isNotBlank
    )

    return value
}

@Composable
fun inputStr(showedValue: ()->String,
             enabled: Boolean = true
             , onValueChange: (String)->Unit
             , modifier: Modifier = Modifier,
             trailingIcon: @Composable ()->Unit={},
             label: String, valid: (String)->Boolean, keyboardOptions: KeyboardOptions = KeyboardOptions.Default): Value<String>{
    return input(showedValue, showedValue(), enabled, onValueChange, modifier, trailingIcon, label, valid, keyboardOptions)
}

@Composable
fun <T> input(showedValue: ()->String, realValue: T,
              enabled: Boolean = true
              , onValueChange: (String)->Unit
              , modifier: Modifier = Modifier,
              trailingIcon: @Composable ()->Unit={},
              label: String, valid: (String)->Boolean, keyboardOptions: KeyboardOptions = KeyboardOptions.Default): Value<T>{
    val valueItself = showedValue()
    var wrongValue: Boolean by remember { mutableStateOf(false) }
    val validate: (String)->Unit = { wrongValue = !valid(it) }

    OutlinedTextField(
        value = valueItself
        , onValueChange = {
            onValueChange(it)
            validate(valueItself)
        }
        , label = { Text(label) }
        , singleLine = true
        , isError = wrongValue
        , trailingIcon = {if (wrongValue) ErrorIcon() else trailingIcon()}
        , keyboardOptions = keyboardOptions
        , enabled = enabled
        , modifier = modifier
    )

    return Value(realValue, {wrongValue}, validate, showedValue)
}

@Composable
fun DateIcon(){
    Icon(Icons.Filled.CalendarToday, "Fecha", tint = MaterialTheme.colorScheme.primary)
}