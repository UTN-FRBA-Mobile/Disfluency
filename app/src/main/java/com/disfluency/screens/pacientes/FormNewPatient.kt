package com.disfluency.screens.pacientes


import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.core.text.isDigitsOnly
import java.time.LocalDate

/*TODO:
 * * Al enviar deberian saltar todos los errores, o bien el boton deberia estar deshabilitado
 * * No pude sacar el espacio del input numero
 */

@Composable
fun FormNewPatient(onSubmit: (_:Patient)->Unit){
    val DEFAULT_KEY_OPTIONS = KeyboardOptions(imeAction = ImeAction.Next)
    val CAPITALIZE_WORDS = DEFAULT_KEY_OPTIONS.copy(capitalization = KeyboardCapitalization.Words)

    val patientName = inputString("Nombre", keyboardOptions = CAPITALIZE_WORDS)
    val patientLastname = inputString(label = "Apellido", keyboardOptions = CAPITALIZE_WORDS)
    val patientDNI = inputString("DNI"
        , validations = listOf { it.isDigitsOnly() }
        ,  keyboardOptions = DEFAULT_KEY_OPTIONS.copy(
            keyboardType = KeyboardType.Decimal
        )
    )

    val todaysDate = LocalDate.now()
    val birthDate: Input<LocalDate> = inputDate(label = "Fecha Nacimiento", todaysDate)

    Button(
        onClick = {
            val attributes = listOf(patientName, patientLastname, patientDNI, birthDate)
            attributes.forEach {it.validate()}
            if(attributes.all { !it.wrongValue() }) {
                val patient = Patient(patientName.value, patientLastname.value, patientDNI.value, birthDate.value, todaysDate)
                onSubmit(patient)
            }
        }
    ) {
        Text("Crear")
    }
}