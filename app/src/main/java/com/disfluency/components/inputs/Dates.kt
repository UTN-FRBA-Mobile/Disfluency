package com.disfluency.components.inputs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.disfluency.R
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

//TODO: Investigar si hay forma de cambiar el formato de las fechas en el DatePicker. Esta como mm/dd/aaaa.

@Composable
fun inputDate(label: String, maxDate: LocalDate?): Input<LocalDate?> {
    var dateValue: LocalDate? by remember {
        mutableStateOf(null)
    }
    var formattedValue by rememberSaveable {
        mutableStateOf("")
    }

    val datePickerState = rememberDatePickerState(initialDisplayMode = DisplayMode.Input)
    var openDialog by remember { mutableStateOf(false) }

    /* * * * */
    val disableDialog = {openDialog = false}
    var input: Input<LocalDate?>? = null

    Box {
        input = input(
            label = label,
            getRealValue = {dateValue},
            formattedValue = {formattedValue},
            onValueChange = {},
            trailingIcon = { DateIcon() }
        )

        Box(modifier = Modifier
            .matchParentSize()
            .clickable { openDialog = true })
    }

    val maxDateAsMilliseconds: Long? = maxDate?.atStartOfDay(ZoneOffset.UTC)?.toInstant()?.toEpochMilli()
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

    return input!!
}

fun millisecondsToLocalDate(milliseconds: Long): LocalDate {
    return Instant.ofEpochMilli(milliseconds)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
}

@Composable
fun DateIcon(){
    Icon(Icons.Filled.CalendarToday, "Fecha", tint = MaterialTheme.colorScheme.primary)
}