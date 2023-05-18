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
    val _notblank = String::isNotBlank
    val patientName = inputString("Nombre", _notblank)
    val patientLastname = inputString(label = "Apellido", valid = _notblank)
    val dni = inputString("DNI"
        , valid = { _notblank(it) && it.isDigitsOnly() }
        ,  keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal
        )
    )

    val bornDate = inputDate(label = "Fecha Nacimiento")

    Button(
        onClick = {
            val attributes = listOf(patientName, patientLastname, dni)
            attributes.forEach {it.validate()}
            if(attributes.all { !it.wrongValue() }) {
                val patient = Patient(patientName.value, patientLastname.value, dni.value, bornDate)
                onSubmit(patient)
            }
        }
    ) {
        Text("Crear")
    }
}

data class Value(val value: String, val wrongValue: ()->Boolean, val validate: ()->Unit)

@Composable
fun inputString(label: String, valid: (String)->Boolean, keyboardOptions: KeyboardOptions=KeyboardOptions.Default): Value{
    var value by remember { mutableStateOf("") }
    var wrongValue: Boolean by remember { mutableStateOf(false) }
    val validate = {wrongValue = !valid(value)}

    OutlinedTextField(
        value = value
        , onValueChange = {
            value = it
            validate()
        }
        , label = { Text(label) }
        , singleLine = true
        , isError = wrongValue
        , trailingIcon = {if (wrongValue) ErrorIcon()}
        , keyboardOptions = keyboardOptions

    )

    return Value(value, {wrongValue},validate)
}

@Composable
fun ErrorIcon(){
    Icon(Icons.Filled.Info, "Error", tint = MaterialTheme.colorScheme.error)
}