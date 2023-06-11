package com.disfluency.screens;

import android.util.Patterns
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import androidx.navigation.NavController
import com.disfluency.R
import com.disfluency.data.PatientRepository
import com.disfluency.model.Patient
import com.disfluency.navigation.Route
import com.disfluency.components.inputs.inputDate
import com.disfluency.components.inputs.inputImage
import com.disfluency.components.inputs.inputString
import java.time.LocalDate

@Composable
fun FormNewPatient(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 10.dp)
        ,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val NEXT_INPUT_ON_ENTER = KeyboardOptions(imeAction = ImeAction.Next)
        val CAPITALIZE_WORDS =
            NEXT_INPUT_ON_ENTER.copy(capitalization = KeyboardCapitalization.Words)

        val patientPhoto = inputImage()

        val patientName = inputString("Nombre", keyboardOptions = CAPITALIZE_WORDS)
        val patientLastname = inputString(label = "Apellido", keyboardOptions = CAPITALIZE_WORDS)
        val patientDNI = inputString(
            "DNI",
            validations = listOf { it.isDigitsOnly() },
            keyboardOptions = NEXT_INPUT_ON_ENTER.copy(keyboardType = KeyboardType.NumberPassword)
        )

        val email = inputString(label = "Correo Electrónico", keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email, imeAction = ImeAction.Done
        ), validations = listOf { Patterns.EMAIL_ADDRESS.asPredicate().test(it) })

        val todaysDate = LocalDate.now()
        val patientBirthDate = inputDate("Fecha de Nacimiento", maxDate = todaysDate)

        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            Button(
                onClick = {
                    val attributes =
                        listOf(patientName, patientLastname, patientDNI, email, patientBirthDate)
                    attributes.forEach { it.validate() }
                    if (attributes.all { !it.wrongValue() }) {
                        val patient = Patient(
                            name = patientName.value,
                            lastName = patientLastname.value,
                            id = patientDNI.value.toInt(),
                            dateOfBirth = patientBirthDate.value!!,
                            email = email.value,
                            joinedSince = todaysDate,
                            weeklyHour = "",
                            weeklyTurn = "",
                            profilePic = R.drawable.avatar_12
                        )
                        PatientRepository.addPatient(patient)
                        navController.navigate(Route.Paciente.routeTo(patient.id))
                    }
                }
            ) {
                Text("Crear")
            }
            Button(
                colors =  ButtonDefaults.buttonColors(
                    contentColorFor(MaterialTheme.colorScheme.inversePrimary)
                ),
                onClick = {navController.navigate(Route.Pacientes.route)}
            ) {
                Text(text = "Cancelar")
            }
        }

    }
}


