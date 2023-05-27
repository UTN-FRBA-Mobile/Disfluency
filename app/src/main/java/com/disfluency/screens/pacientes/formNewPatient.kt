package com.disfluency.screens.pacientes

import android.util.Patterns
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.core.text.isDigitsOnly
import java.time.LocalDate
/*TODO
 * * Ver que hacer con imagen
 * * Reemplazar fecha por date picker
 */

@Composable
fun FormNewPatient(onSubmit: (_:Patient)->Unit){
    val NEXT_INPUT_ON_ENTER = KeyboardOptions(imeAction = ImeAction.Next)
    val CAPITALIZE_WORDS = NEXT_INPUT_ON_ENTER.copy(capitalization = KeyboardCapitalization.Words)

    val patientPhoto = inputImage()

    val patientName = inputString("Nombre", keyboardOptions = CAPITALIZE_WORDS)
    val patientLastname = inputString(label = "Apellido", keyboardOptions = CAPITALIZE_WORDS)
    val patientDNI = inputString("DNI"
        , validations = listOf { it.isDigitsOnly() }
        ,  keyboardOptions = NEXT_INPUT_ON_ENTER.copy(
            keyboardType = KeyboardType.NumberPassword
        )
    )

    val email = inputString(label = "Correo Electrónico", keyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Email
        , imeAction = ImeAction.Done
    ), validations = listOf { Patterns.EMAIL_ADDRESS.asPredicate().test(it) })

    val todaysDate = LocalDate.now()
    val patientBirthDate: Input<LocalDate> = inputDate(label = "Fecha Nacimiento", todaysDate)

    Button(
        onClick = {
            val attributes = listOf(patientName, patientLastname, patientDNI, patientBirthDate, email)
            attributes.forEach {it.validate()}
            if(attributes.all { !it.wrongValue() }) {
                val patient = Patient(patientName.value, patientLastname.value, patientDNI.value, patientBirthDate.value, email.value, todaysDate)
                onSubmit(patient)
            }
        }
    ) {
        Text("Crear")
    }
}