package com.disfluency.screens.pacientes

import android.util.Patterns
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.core.text.isDigitsOnly
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

/*TODO
 * * Ver que hacer con imagen
 * * Reemplazar fecha por date picker
 */

@Composable
fun FormNewPatient(onSubmit: (_: Patient) -> Unit) {
    val NEXT_INPUT_ON_ENTER = KeyboardOptions(imeAction = ImeAction.Next)
    val CAPITALIZE_WORDS = NEXT_INPUT_ON_ENTER.copy(capitalization = KeyboardCapitalization.Words)

    val patientPhoto = inputImage()

    val patientName = inputString("Nombre", keyboardOptions = CAPITALIZE_WORDS)
    val patientLastname = inputString(label = "Apellido", keyboardOptions = CAPITALIZE_WORDS)
    val patientDNI = inputString("DNI",
        validations = listOf { it.isDigitsOnly() },
        keyboardOptions = NEXT_INPUT_ON_ENTER.copy(
            keyboardType = KeyboardType.NumberPassword
        )
    )

    val email = inputString(label = "Correo Electrónico", keyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Email, imeAction = ImeAction.Done
    ), validations = listOf { Patterns.EMAIL_ADDRESS.asPredicate().test(it) })

    val todaysDate = LocalDate.now()


    var patientBirthDate by rememberSaveable {
        mutableStateOf("")
    }
    val datePickerState = rememberDatePickerState()
    val openDialog = remember {
        mutableStateOf(false)
    }

    Box {
        OutlinedTextField(
            value = patientBirthDate,
            onValueChange = { openDialog.value = false },
            label = { Text(text = "Fecha de nacimiento") }
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .clickable(onClick = { openDialog.value = true }),
        )
    }

    if (openDialog.value) {
        DatePickerDialog(onDismissRequest = { openDialog.value = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                        val calendar = Calendar.getInstance()
                        calendar.timeInMillis = datePickerState.selectedDateMillis!!
                        patientBirthDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                            .format(calendar.time)
                    }
                ) {
                    Text("Ok")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                    }
                ) {
                    Text("Salir")
                }
            }) {
            DatePicker(state = datePickerState)
        }
    }


    Button(
        onClick = {
            val attributes = listOf(patientName, patientLastname, patientDNI, email)
            attributes.forEach { it.validate() }
            if (attributes.all { !it.wrongValue() }) {
                val patient = Patient(
                    patientName.value,
                    patientLastname.value,
                    patientDNI.value,
                    todaysDate,
                    email.value,
                    todaysDate
                )
                onSubmit(patient)
            }
        }
    ) {
        Text("Crear")
    }
}