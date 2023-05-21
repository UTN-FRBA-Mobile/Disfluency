package com.disfluency.screens.pacientes


import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.core.text.isDigitsOnly
import java.time.LocalDate

/*TODO:
 * * Al enviar deberian saltar todos los errores, o bien el boton deberia estar deshabilitado
 * * No pude sacar el espacio del input numero
 * * Al salir y volver a entrar a la pagina de pacientes, se eliminan. Esto es por el scope de la variable. A futuro habrá que pasará por parámetro
 */

@Composable
fun FormNewPatient(onSubmit: (_:Patient)->Unit){
    val CAPITALIZE_WORDS = KeyboardOptions(capitalization = KeyboardCapitalization.Words)

    val patientName = inputString("Nombre", keyboardOptions = CAPITALIZE_WORDS)
    val patientLastname = inputString(label = "Apellido", keyboardOptions = CAPITALIZE_WORDS)
    val patientDNI = inputString("DNI"
        , validations = listOf { it.isDigitsOnly() }
        ,  keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal
        )
    )
    val birthDate: Input<LocalDate> = inputDate(label = "Fecha Nacimiento")

    Button(
        onClick = {
            val attributes = listOf(patientName, patientLastname, patientDNI, birthDate)
            attributes.forEach {it.validate()}
            if(attributes.all { !it.wrongValue() }) {
                val patient = Patient(patientName.value, patientLastname.value, patientDNI.value, birthDate.value)
                onSubmit(patient)
            }
        }
    ) {
        Text("Crear")
    }
}