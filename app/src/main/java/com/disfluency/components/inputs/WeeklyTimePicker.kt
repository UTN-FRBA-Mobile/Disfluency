package com.disfluency.components.inputs

import android.app.TimePickerDialog
import android.widget.TimePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

@Composable
fun WeeklyTimePicker(state: MutableState<LocalTime?>, label: String){

    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    var formattedValue by remember { mutableStateOf(formatToString(state)) }

    // Fetching current hour, and minute
    val hour = calendar[Calendar.HOUR_OF_DAY]
    val minute = calendar[Calendar.MINUTE]

    val onTimeSetListener: (TimePicker, Int, Int) -> Unit = { _, selectedHour, selectedMinute ->
        state.value = LocalTime.of(selectedHour, selectedMinute)
        formattedValue = formatToString(state)
    }

    val timePicker = TimePickerDialog(context, onTimeSetListener, hour, minute, true)

    var wrongValue: Boolean by remember { mutableStateOf(false) }

    Box() {
        OutlinedTextField(
            value = formattedValue,
            onValueChange = {
                wrongValue = !MandatoryValidation().validate(formattedValue)
            },
            label = { Text(label) },
            singleLine = true,
            isError = wrongValue,
            trailingIcon = {
                if (wrongValue) Icon(Icons.Filled.Info, "Error")
                else Icon(Icons.Filled.AccessTime, "Hora", tint = MaterialTheme.colorScheme.primary)
            },
            supportingText = {
                if(wrongValue) Text(text = "Este campo es requerido")
            }
        )

        Box(modifier = Modifier
            .matchParentSize()
            .clickable { timePicker.show() })
    }
}

fun formatToString(state: MutableState<LocalTime?>): String{
    return state.value?.format(DateTimeFormatter.ofPattern("HH:mm")) ?: ""
}