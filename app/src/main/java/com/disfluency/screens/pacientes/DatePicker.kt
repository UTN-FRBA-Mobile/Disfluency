package com.disfluency.screens.pacientes

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun inputDate(label: String): LocalDate{
    var dateValue by remember { mutableStateOf(LocalDate.now()) }
    var showedDateValue by remember { mutableStateOf("")}
    val datePicker = DatePickerDialog(LocalContext.current,
        { _: DatePicker, year, month, day ->
            dateValue = LocalDate.of(year, month, day)
            showedDateValue = dateValue.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        }
        , dateValue.year, dateValue.monthValue, dateValue.dayOfMonth
    )

    OutlinedTextField(
        label = {Text(label)}
        , value = showedDateValue, onValueChange = {}
        , enabled = false
        , modifier = Modifier.clickable { datePicker.show() }
        , trailingIcon = {DateIcon()}
    )

    return dateValue
}
@Composable
fun DateIcon(){
    Icon(Icons.Filled.CalendarToday, "Fecha Nacimiento", tint = MaterialTheme.colorScheme.primary)
}