package com.disfluency.screens.pacientes

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.KeyboardType
import androidx.core.text.isDigitsOnly

/*TODO:
 * * Al enviar deberian saltar todos los errores, o bien el boton deberia estar deshabilitado
 * * No pude sacar el espacio del input numero
 * * Al salir y volver a entrar a la pagina de pacientes, se eliminan. Esto es por el scope de la variable. A futuro habrá que pasará por parámetro
 */

@Composable
fun FormNewPatient(onSubmit: (_:Patient)->Unit){
    var patientName by remember { mutableStateOf("") }
    var wrongName by remember { mutableStateOf(false) }
    val validateName = {wrongName = patientName.isBlank()}

    OutlinedTextField(
        value = patientName
        , onValueChange = {
            patientName = it
            validateName()
        }
        , label = { Text("Nombre") }
        , singleLine = true
        , isError = wrongName
        , trailingIcon = {ShowIconInCaseOfError(isError = wrongName)}
    )

    var patientLastname by remember { mutableStateOf("") }
    var wrongLastname by remember { mutableStateOf(false) }
    val validateLastname = {wrongLastname = patientLastname.isBlank()}
    OutlinedTextField(
        value = patientLastname
        , onValueChange = {
            patientLastname = it
            validateLastname()
        }
        , label = { Text("Apellido") }
        , singleLine = true
        , isError = wrongLastname
        , trailingIcon = {ShowIconInCaseOfError(isError = wrongLastname)}

    )

    var dni by remember { mutableStateOf("") }
    var wrongDNI by remember { mutableStateOf(false) }
    val validateDNI = { wrongDNI = dni.isBlank() || !dni.isDigitsOnly() }
    OutlinedTextField(
        value = dni,
        onValueChange = {
            dni = it
            validateDNI()
        }
        , label = { Text(text = "DNI") }
        , singleLine = true
        , keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal
        )
        , isError = wrongDNI
        , trailingIcon = {ShowIconInCaseOfError(isError = wrongDNI)}

    )

    Button(
        onClick = {
            val patient = Patient(patientName, patientLastname, dni)
            listOf(validateName, validateLastname, validateDNI)
                .forEach { it() }
            if(!wrongName && !wrongLastname && !wrongDNI){
                onSubmit(patient)
            }
        }
    ) {
        Text("Crear")
    }

}

@Composable
fun ShowIconInCaseOfError(isError: Boolean){
    if (isError)
        Icon(Icons.Filled.Info, "Error", tint = MaterialTheme.colorScheme.error)
}