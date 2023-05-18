package com.disfluency.screens.pacientes

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.core.text.isDigitsOnly

/*TODO:
 * * Al enviar deberian saltar todos los errores, o bien el boton deberia estar deshabilitado
 * * No pude sacar el espacio del input numero
 * * Al salir y volver a entrar a la pagina de pacientes, se eliminan. Esto es por el scope de la variable. A futuro habrá que pasará por parámetro
 */

class Value(val value: String, val wrongValue: ()->Boolean, val validate: ()->Unit)

@Composable
fun FormNewPatient(onSubmit: (_:Patient)->Unit){
    val _notblank = String::isNotBlank
    val patientName = inputValue("Nombre", _notblank)
    val patientLastname = inputValue(label = "Apellido", valid = _notblank)
    val dni = inputValue("DNI"
        , valid = { _notblank(it) && it.isDigitsOnly() }
        ,  keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal
        )
    )



    Button(
        onClick = {
            val patient = Patient(patientName.value, patientLastname.value, dni.value)
            val attributes = listOf(patientName, patientLastname, dni)
            attributes.forEach {it.validate()}
            if(attributes.all { !it.wrongValue() }) {
                onSubmit(patient)
            }
        }
    ) {
        Text("Crear")
    }
}

@Composable
fun inputValue(label: String, valid: (String)->Boolean, keyboardOptions: KeyboardOptions=KeyboardOptions.Default): Value{
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