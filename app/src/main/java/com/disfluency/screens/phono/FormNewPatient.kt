package com.disfluency.screens.phono;

import android.util.Patterns
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import androidx.navigation.NavController
import com.disfluency.R
import com.disfluency.components.inputs.*
import com.disfluency.data.PatientRepository
import com.disfluency.model.Patient
import com.disfluency.model.Phono
import com.disfluency.model.utils.DayOfWeek
import com.disfluency.navigation.Route
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun FormNewPatient(navController: NavController, phono: Phono) {
    val NEXT_INPUT_ON_ENTER = KeyboardOptions(imeAction = ImeAction.Next)
    val NEXT_AND_CAPITALIZE_WORDS = NEXT_INPUT_ON_ENTER.copy(capitalization = KeyboardCapitalization.Words)

    val avatarIndex = remember { mutableStateOf(0) }
    val todaysDate = LocalDate.now()
    val weeklyTurn = remember{ mutableStateListOf<DayOfWeek>() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
            .verticalScroll(rememberScrollState())
            .padding(all = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AvatarPicker(selectedAvatarIndex = avatarIndex)

        //TODO: hacer bien estos
        val patientName = inputAsString(label = stringResource(R.string.new_patient_label_name), keyboardOptions = NEXT_AND_CAPITALIZE_WORDS)
        val patientLastname = inputAsString(stringResource(R.string.new_patient_label_lastname), keyboardOptions = NEXT_AND_CAPITALIZE_WORDS)
        val patientDNI = inputAsString(
            stringResource(R.string.new_patient_label_dni),
            validations = listOf { it.isDigitsOnly() },
            keyboardOptions = NEXT_INPUT_ON_ENTER.copy(keyboardType = KeyboardType.NumberPassword)
        )

        val email = inputAsString(label = stringResource(R.string.new_patient_label_email), keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email, imeAction = ImeAction.Done
        ), validations = listOf { Patterns.EMAIL_ADDRESS.asPredicate().test(it) })

        val patientBirthDate = inputDate(stringResource(R.string.new_patient_label_dayOfBirth), maxDate = todaysDate)

        //TODO: Agregar validacion de que elija al menos 1.
        Text(text = stringResource(R.string.new_patient_label_weeklyTurn), color = MaterialTheme.colorScheme.primary)
        WeeklyTurnPicker(selectedDays = weeklyTurn)

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
                            /* Este value es nullable porque la fecha hasta que se elija va a ser null.
                             * Al agregar !! "casteo" de nullable a no nullable (si fuera null, romperia).
                             * En el validate se valida que no sea null.
                             */
                            email = email.value,
                            joinedSince = todaysDate,
                            weeklyHour = LocalTime.of(18,0), //TODO
                            weeklyTurn = weeklyTurn,
                            profilePic = PICKABLE_AVATARS[avatarIndex.value]
                        )

                        phono.addPatient(patient)
                        PatientRepository.addPatient(patient)
                        navController.navigate(Route.Paciente.routeTo(patient.id))
                    }
                }
            ) {
                Text(stringResource(R.string.new_patient_button_submit))
            }
            Button(
                colors =  ButtonDefaults.buttonColors(
                    contentColorFor(MaterialTheme.colorScheme.inversePrimary)
                ),
                onClick = {navController.navigate(Route.Pacientes.route)}
            ) {
                Text(text = stringResource(R.string.new_patient_button_cancel))
            }
        }

    }
}


