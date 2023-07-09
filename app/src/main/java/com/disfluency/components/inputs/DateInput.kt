package com.disfluency.components.inputs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.DialogProperties
import com.disfluency.R
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

//TODO: Investigar si hay forma de cambiar el formato de las fechas en el DatePicker. Esta como mm/dd/aaaa.

@Composable
fun DateInput(state: MutableState<LocalDate?>, label: String){
    var formattedValue by rememberSaveable { mutableStateOf(formatToString(state)) }
    val datePickerState = rememberDatePickerState(initialDisplayMode = DisplayMode.Input)
    var openDialog by remember { mutableStateOf(false) }

    val disableDialog = { openDialog = false }

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
                else Icon(Icons.Filled.CalendarToday, "Fecha", tint = MaterialTheme.colorScheme.primary)
            },
            supportingText = {
                if(wrongValue) Text(text = "Este campo es requerido")
            }
        )

        Box(modifier = Modifier
            .matchParentSize()
            .clickable { openDialog = true })
    }

    val maxDateAsMilliseconds: Long? = LocalDate.now().atStartOfDay(ZoneOffset.UTC)?.toInstant()?.toEpochMilli()
    if (openDialog) {
        DatePickerDialog(
            onDismissRequest = disableDialog,
            confirmButton = {
                TextButton(
                    onClick = {
                        state.value = millisecondsToLocalDate(datePickerState.selectedDateMillis!!)
                        formattedValue = formatToString(state)
                        disableDialog()
                    },
                    enabled = datePickerState.selectedDateMillis!=null
                ) {
                    Text(stringResource(R.string.new_patient_dateOfBirth_button_accept))
                }
            },
            dismissButton = {
                TextButton(disableDialog) {
                    Text(stringResource(R.string.new_patient_dateOfBirth_button_return))
                }
            },
            content = {
                DatePicker(state = datePickerState, title = null, headline = null, showModeToggle = true,
                    dateValidator = {
                        maxDateAsMilliseconds!=null && it < maxDateAsMilliseconds
                    }
                )
            }
        )
    }
}

fun formatToString(state: MutableState<LocalDate?>): String{
    return state.value?.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) ?: ""
}

fun millisecondsToLocalDate(milliseconds: Long): LocalDate {
    return Instant.ofEpochMilli(milliseconds)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
}