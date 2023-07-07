package com.disfluency.screens.phono;

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.disfluency.AppScaffold
import com.disfluency.R
import com.disfluency.components.inputs.*
import com.disfluency.components.stepper.PageStepper
import com.disfluency.components.stepper.StepScreen
import com.disfluency.components.user.IconLabeled
import com.disfluency.components.user.PatientInfoCard
import com.disfluency.components.user.weeklyTurnFormat
import com.disfluency.data.PatientRepository
import com.disfluency.model.Patient
import com.disfluency.model.Phono
import com.disfluency.model.utils.DayOfWeek
import com.disfluency.navigation.Route
import com.disfluency.navigation.bottomNavigation.BottomNavigationItem
import com.disfluency.ui.theme.MyApplicationTheme
import com.maryamrzdh.stepper.Stepper
import java.time.LocalDate
import java.time.LocalTime
import java.time.Period
import java.time.format.DateTimeFormatter

@Composable
fun _Old_FormNewPatient(navController: NavController, phono: Phono) {
    val avatarIndex = remember { mutableStateOf(0) }
    val name = remember { mutableStateOf("") }
    val lastName = remember { mutableStateOf("") }
    val dni = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val dateOfBirth: MutableState<LocalDate?> = remember { mutableStateOf(null) }
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
        MandatoryTextInput(state = name, label = stringResource(R.string.new_patient_label_name))
        MandatoryTextInput(state = lastName, label = stringResource(R.string.new_patient_label_lastname))
        MandatoryDigitsInput(state = dni, label = stringResource(R.string.new_patient_label_dni))
        MandatoryEmailInput(state = email, label = stringResource(R.string.new_patient_label_email))
        DateInput(state = dateOfBirth, label = stringResource(R.string.new_patient_label_dayOfBirth))
        WeeklyTurnPicker(selectedDays = weeklyTurn)

        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            Button(
                onClick = {
                    //TODO: que al no ser posible te indique con algun mensaje.
                    if (validateInputs(name.value, lastName.value, dni.value, email.value, dateOfBirth.value, weeklyTurn)) {

                        val patient = Patient(
                            name = name.value,
                            lastName = lastName.value,
                            id = dni.value.toInt(),
                            dateOfBirth = dateOfBirth.value!!,
                            /* Este value es nullable porque la fecha hasta que se elija va a ser null.
                             * Al agregar !! "casteo" de nullable a no nullable (si fuera null, romperia).
                             * En el validate se valida que no sea null.
                             */
                            email = email.value,
                            joinedSince = LocalDate.now(),
                            weeklyHour = LocalTime.of(18,0), //TODO
                            weeklyTurn = weeklyTurn,
                            profilePic = USER_AVATARS[avatarIndex.value]
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

fun validateInputs(name: String, lastName: String, dni: String, email: String, dateOfBirth: LocalDate?, weeklyTurn: List<DayOfWeek>): Boolean {
    return listOf(name, lastName, dni, email).all { MandatoryValidation().validate(it) }
            && DigitsOnlyValidation().validate(dni)
            && EmailValidation().validate(email)
            && dateOfBirth != null
            && weeklyTurn.isNotEmpty()
}

@Composable
fun FormNewPatient(navController: NavController, phono: Phono){
    val avatarIndex = remember { mutableStateOf(0) }
    val name = remember { mutableStateOf("") }
    val lastName = remember { mutableStateOf("") }
    val dni = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val dateOfBirth: MutableState<LocalDate?> = remember { mutableStateOf(null) }
    val weeklyTurn = remember{ mutableStateListOf<DayOfWeek>() }
    val weeklyHour: MutableState<LocalTime?> = remember { mutableStateOf(null) }

    val steps = listOf(
        StepScreen("Avatar"){
            AvatarSelectionScreen(avatarIndex = avatarIndex)
        },
        StepScreen("Datos", validateDataInputs(name, lastName, dni, email, dateOfBirth)){
            PatientDataScreen(name = name, lastName = lastName, id = dni, email = email, dateOfBirth = dateOfBirth)
        },
        StepScreen("Turno", validateTurnInputs(weeklyTurn, weeklyHour)){
            TurnSelectionScreen(weeklyTurn = weeklyTurn, weeklyHour = weeklyHour)
        },
        StepScreen("Confirmar"){
            NewPatientConfirmationScreen(
                avatar = USER_AVATARS[avatarIndex.value],
                name = name.value,
                lastName = lastName.value,
                id = dni.value,
                email = email.value,
                dateOfBirth = dateOfBirth.value!!,
                weeklyTurn = weeklyTurn,
                weeklyHour = weeklyHour.value!!
            )
        }
    )

    PageStepper(
        steps = steps,
        onCancel = {
            navController.popBackStack()
        },
        onFinish = {
            val patient = Patient(
                name = name.value,
                lastName = lastName.value,
                id = dni.value.toInt(),
                dateOfBirth = dateOfBirth.value!!,
                email = email.value,
                joinedSince = LocalDate.now(),
                weeklyHour = weeklyHour.value!!,
                weeklyTurn = weeklyTurn,
                profilePic = USER_AVATARS[avatarIndex.value]
            )

            phono.addPatient(patient)
            PatientRepository.addPatient(patient)
            navController.navigate(Route.Paciente.routeTo(patient.id))
        }
    )
}

@Composable
fun AvatarSelectionScreen(avatarIndex: MutableState<Int>){
    AvatarPicker(selectedAvatarIndex = avatarIndex)
}

@Composable
fun PatientDataScreen(name: MutableState<String>, lastName: MutableState<String>, id: MutableState<String>, email: MutableState<String>, dateOfBirth: MutableState<LocalDate?>){
    Column() {
        MandatoryTextInput(state = name, label = stringResource(R.string.new_patient_label_name))
        MandatoryTextInput(state = lastName, label = stringResource(R.string.new_patient_label_lastname))
        MandatoryDigitsInput(state = id, label = stringResource(R.string.new_patient_label_dni))
        MandatoryEmailInput(state = email, label = stringResource(R.string.new_patient_label_email))
        DateInput(state = dateOfBirth, label = stringResource(R.string.new_patient_label_dayOfBirth))
    }
}

fun validateDataInputs(name: MutableState<String>, lastName: MutableState<String>, id: MutableState<String>, email: MutableState<String>, dateOfBirth: MutableState<LocalDate?>): Boolean {
    return listOf(name, lastName, id, email).all { MandatoryValidation().validate(it.value) }
            && DigitsOnlyValidation().validate(id.value)
            && EmailValidation().validate(email.value)
            && dateOfBirth.value != null
}

@Composable
fun TurnSelectionScreen(weeklyTurn: SnapshotStateList<DayOfWeek>, weeklyHour: MutableState<LocalTime?>){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(R.string.new_patient_label_weeklyTurn), color = MaterialTheme.colorScheme.primary)
        WeeklyTurnPicker(selectedDays = weeklyTurn)
        Spacer(modifier = Modifier.size(16.dp))
        Text(text = stringResource(R.string.new_patient_label_weeklyHour), color = MaterialTheme.colorScheme.primary)
        WeeklyTimePicker(state = weeklyHour, stringResource(R.string.new_patient_label_weeklyHour))
    }
}

fun validateTurnInputs(weeklyTurn: SnapshotStateList<DayOfWeek>, weeklyHour: MutableState<LocalTime?>): Boolean{
    return weeklyTurn.isNotEmpty() && weeklyHour.value != null
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun NewPatientConfirmationScreen(avatar: Int, name: String, lastName: String, id: String, email: String, dateOfBirth: LocalDate, weeklyTurn: List<DayOfWeek>, weeklyHour: LocalTime){

    Column(modifier = Modifier.width(280.dp)) {
        Card(
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = avatar),
                    contentDescription = "User Thumbnail",
                    modifier = Modifier
                        .size(90.dp)
                        .padding(bottom = 8.dp)
                )

                Text(
                    text = "$name $lastName",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = id,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }
        }

        Card(
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ){
            FlowRow(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                val age = Period.between(dateOfBirth, LocalDate.now()).years
                IconLabeled(
                    icon = Icons.Outlined.AutoAwesome,
                    label = "$age años",
                    content = "Edad"
                )

                Spacer(modifier = Modifier.width(16.dp))

                val date = dateOfBirth.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                IconLabeled(
                    icon = Icons.Outlined.Cake,
                    label = "nacido un $date",
                    content = "Fecha de Nacimiento"
                )
            }
        }

        Card(
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ){
            Box(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                IconLabeled(
                    icon = Icons.Outlined.ForwardToInbox,
                    label = email,
                    content = "Email"
                )
            }
        }

        Card(
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ){
            FlowRow(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                IconLabeled(
                    icon = Icons.Outlined.CalendarMonth,
                    label = weeklyTurnFormat(weeklyTurn),
                    content = "Turn"
                )

                Spacer(modifier = Modifier.width(16.dp))

                IconLabeled(
                    icon = Icons.Outlined.AccessTime,
                    label = weeklyHour.format(DateTimeFormatter.ofPattern(stringResource(R.string.time_format))),
                    content = "Time"
                )
            }
        }


    }
}
