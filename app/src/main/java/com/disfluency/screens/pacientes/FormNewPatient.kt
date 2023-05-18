package com.disfluency.screens.pacientes


import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.KeyboardType
import androidx.core.text.isDigitsOnly
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/*TODO:
 * * Al enviar deberian saltar todos los errores, o bien el boton deberia estar deshabilitado
 * * No pude sacar el espacio del input numero
 * * Al salir y volver a entrar a la pagina de pacientes, se eliminan. Esto es por el scope de la variable. A futuro habrá que pasará por parámetro
 */

@Composable
fun FormNewPatient(onSubmit: (_:Patient)->Unit){
    val _notblank = String::isNotBlank
    val patientName = inputString("Nombre", _notblank)
    val patientLastname = inputString(label = "Apellido", valid = _notblank)
    val dni = inputString("DNI"
        , valid = { _notblank(it) && it.isDigitsOnly() }
        ,  keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal
        )
    )

    val bornDate: Value<LocalDate> = inputDate(label = "Fecha Nacimiento")

    Button(
        onClick = {
            val attributes = listOf(patientName, patientLastname, dni, bornDate)
            attributes.forEach {it.validate()}
            if(attributes.all { !it.wrongValue() }) {
                val patient = Patient(patientName.value, patientLastname.value, dni.value, bornDate.value)
                onSubmit(patient)
            }
        }
    ) {
        Text("Crear")
    }
}