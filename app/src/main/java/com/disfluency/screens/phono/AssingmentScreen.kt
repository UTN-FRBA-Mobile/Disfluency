package com.disfluency.screens.phono

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.disfluency.data.ExerciseRepository
import com.disfluency.data.PatientRepository
import com.disfluency.model.Exercise
import com.disfluency.model.Patient
import com.disfluency.model.Phono
import com.disfluency.model.dto.AssignmentDTO
import com.disfluency.navigation.Route
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun AssignmentScreen(navController: NavHostController, phono: Phono) {
    val checkedPatients = remember { mutableStateListOf<String>() }
    val checkedExercises = remember { mutableStateListOf<String>() }

    val patients = remember { mutableStateListOf<Patient>() }
    val exercises = remember { mutableStateListOf<Exercise>() }

    LaunchedEffect(Unit) {
        val patientsResponse = withContext(Dispatchers.IO) { PatientRepository.getPatientsByTherapistId(phono.id) }
        Log.i("HTTP", patientsResponse.toString())
        patients.addAll(patientsResponse)
    }

    LaunchedEffect(Unit) {
        val exercisesResponse = withContext(Dispatchers.IO) { ExerciseRepository.getExercisesByTherapistId(phono.id) }
        Log.i("HTTP", exercisesResponse.toString())
        exercises.addAll(exercisesResponse)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Pacientes",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .padding(16.dp)
        )
        Row(modifier = Modifier.weight(1f)) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                PatientsColumn(patients, checkedPatients, navController)
            }
        }
        Text(
            text = "Ejercicios",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .padding(16.dp)
        )
        Row(modifier = Modifier.weight(1f)) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                ExercisesColumn(exercises, checkedExercises, navController)
            }
        }
    }
    AssignButton(
        checkedExercises = checkedExercises,
        checkedPatients = checkedPatients,
        therapistId = phono.id,
        navController = navController
    )
}

@Composable
fun PatientsColumn(patients: List<Patient>, checkedPatients: MutableList<String>, navController: NavHostController) {
    Column(modifier = Modifier.fillMaxHeight()) {
        Row(modifier = Modifier.fillMaxWidth()) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(4.dp)){
                items(patients){ patient ->
                    PatientAssignmentCard(patient, checkedPatients, navController)
                }
            }
        }
    }
}

@Composable
fun ExercisesColumn(exercises: List<Exercise>, checkedExercises: MutableList<String>, navController: NavHostController) {
    Column(modifier = Modifier.fillMaxHeight()) {
        Row(modifier = Modifier.fillMaxWidth()) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(4.dp)){
                items(exercises){ ex ->
                    ExerciseAssignmentCard(ex, checkedExercises, navController)
                }
            }
        }
    }
}

@Composable
fun PatientAssignmentCard(patient: Patient, checkedPatients: MutableList<String>, navController: NavHostController) {
    Row(modifier = Modifier.padding(2.dp)) {
        val isChecked = remember { mutableStateOf(patient.id in checkedPatients) }
        Checkbox(
            checked = isChecked.value,
            onCheckedChange =  {
                isChecked.value = it
                if (isChecked.value) {
                    if (!checkedPatients.contains(patient.id))
                        checkedPatients.add(patient.id)
                } else {
                    checkedPatients.remove(patient.id)
                }
            })
        ListItem(
            headlineContent = { Text(text = patient.fullName()) },
            modifier = Modifier.clickable {
                navController.navigate(Route.Paciente.routeTo(patient.id))
            }
        )
    }
}

@Composable
fun ExerciseAssignmentCard(exercise: Exercise, checkedExercises: MutableList<String>, navController: NavHostController) {
    Row(modifier = Modifier.padding(2.dp)) {
        val isChecked = remember { mutableStateOf(exercise.id in checkedExercises) }
        Checkbox(
            checked = isChecked.value,
            onCheckedChange =  {
                isChecked.value = it
                if (isChecked.value) {
                    if (!checkedExercises.contains(exercise.id))
                        checkedExercises.add(exercise.id)
                } else {
                    checkedExercises.remove(exercise.id)
                }
            })
        ListItem(
            headlineContent = { Text(text = exercise.title) },
            modifier = Modifier.clickable {
                navController.navigate(Route.Ejercicio.routeTo(exercise.id))
            }
        )
    }
}

@Composable
fun AssignButton(
    checkedExercises: MutableList<String>,
    checkedPatients: MutableList<String>,
    therapistId: String,
    navController: NavHostController
) {
    var onSubmit by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        FloatingActionButton(
            onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    PatientRepository.assignExercisesToPatients(AssignmentDTO(checkedPatients, checkedExercises), therapistId)
                    onSubmit = true
                }
            },
            modifier = Modifier.padding(16.dp),
        ) {
            Icon(Icons.Filled.Send, "Asignar")
        }
    }

    if(onSubmit) {
        LaunchedEffect(Unit) {
            navController.navigate(Route.HomePhono.route)
        }
    }
}