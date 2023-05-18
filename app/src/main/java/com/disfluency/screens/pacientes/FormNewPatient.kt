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
 */

@Composable
fun FormNewPatient(onSubmit: (_:Patient)->Unit){
    var patientName by remember { mutableStateOf("") }
    var wrongName by remember { mutableStateOf(false) }
    //TODO: Esta validacion salta con enter desde la computadora, pero no con el boton del celular. Igualmente tiene que haber otra forma mas facil
    OutlinedTextField(
        value = patientName
        , onValueChange = {
            patientName = it
            wrongName = patientName.isBlank()
        }
        , label = { Text("Nombre") }
        , singleLine = true
        , isError = wrongName
        , trailingIcon = {ShowIconInCaseOfError(isError = wrongName)}
    )

    var patientLastname by remember { mutableStateOf("") }
    var wrongLastname by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = patientLastname
        , onValueChange = {
            patientLastname = it
            wrongLastname = patientLastname.isBlank()
        }
        , label = { Text("Apellido") }
        , singleLine = true
        , isError = wrongLastname
        , trailingIcon = {ShowIconInCaseOfError(isError = wrongLastname)}

    )

    var dni by remember { mutableStateOf("") }
    var wrongDNI by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = dni,
        onValueChange = {
            dni = it
            wrongDNI = dni.isBlank() || dni.contains(" ");
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
            if(whatsWrongWithPatient(patient).isEmpty()){
                onSubmit(patient)
            }
        }
    ) {
        Text("Crear")
    }
}

fun whatsWrongWithPatient(patient: Patient): List<String>{
    val wrongThings = mutableListOf<String>()
    if(patient.name.isBlank()) wrongThings += "Falta ingresar nombre"
    if(patient.lastname.isBlank()) wrongThings += "Falta ingresar apellido"
    if(!patient.dni.isDigitsOnly()) wrongThings += "Falta ingresar numero de DNI"
    return wrongThings
}

@Composable
fun ShowIconInCaseOfError(isError: Boolean){
    if (isError)
        Icon(Icons.Filled.Info, "Error", tint = MaterialTheme.colorScheme.error)
}