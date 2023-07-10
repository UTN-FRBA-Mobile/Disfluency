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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.disfluency.components.list.items.ExerciseListItem
import com.disfluency.components.list.items.PatientListItem
import com.disfluency.data.ExerciseRepository
import com.disfluency.data.PatientRepository
import com.disfluency.loading.SkeletonLoader
import com.disfluency.loading.skeleton.exercise.ExerciseListItemSkeleton
import com.disfluency.loading.skeleton.patient.PatientListItemSkeleton
import com.disfluency.model.Exercise
import com.disfluency.model.Patient
import com.disfluency.model.Phono
import com.disfluency.model.dto.AssignmentDTO
import com.disfluency.navigation.Route
import kotlinx.coroutines.*

@Composable
fun AssignmentScreen(navController: NavHostController, phono: Phono) {
    val checkedPatients = remember { mutableStateListOf<String>() }
    val checkedExercises = remember { mutableStateListOf<String>() }

    val patients: MutableState<List<Patient>?> = remember { mutableStateOf(null) }
    val exercises: MutableState<List<Exercise>?> = remember { mutableStateOf(null) }

    LaunchedEffect(Unit) {
        val patientsResponse = withContext(Dispatchers.IO) { PatientRepository.getPatientsByTherapistId(phono.id) }
        Log.i("HTTP", patientsResponse.toString())
        patients.value = patientsResponse
    }

    LaunchedEffect(Unit) {
        val exercisesResponse = withContext(Dispatchers.IO) { ExerciseRepository.getExercisesByTherapistId(phono.id) }
        Log.i("HTTP", exercisesResponse.toString())
        exercises.value = exercisesResponse
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Pacientes",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
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
                PatientsColumn(patients, checkedPatients)
            }
        }
        Text(
            text = "Ejercicios",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
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
                ExercisesColumn(exercises, checkedExercises)
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
fun PatientsColumn(patients: MutableState<List<Patient>?>, checkedPatients: MutableList<String>) {
    Column(modifier = Modifier.fillMaxHeight()) {
        Row(modifier = Modifier.fillMaxWidth()) {

            SkeletonLoader(
                state = patients,
                content = {
                    patients.value?.let {
                        LazyColumn(verticalArrangement = Arrangement.spacedBy(4.dp)){
                            items(it){ patient ->
                                PatientAssignmentListItem(patient, checkedPatients)
                            }
                        }
                    }
                },
                skeleton = {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(4.dp)){
                        items(3){
                            PatientListItemSkeleton()
                        }
                    }
                }
            )

        }
    }
}

@Composable
fun ExercisesColumn(exercises: MutableState<List<Exercise>?>, checkedExercises: MutableList<String>) {
    Column(modifier = Modifier.fillMaxHeight()) {
        Row(modifier = Modifier.fillMaxWidth()) {

            SkeletonLoader(
                state = exercises,
                content = {
                    exercises.value?.let {
                        LazyColumn(verticalArrangement = Arrangement.spacedBy(4.dp)){
                            items(it){ ex ->
                                ExerciseAssignmentListItem(ex, checkedExercises)
                            }
                        }
                    }
                },
                skeleton = {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(4.dp)){
                        items(3){
                            ExerciseListItemSkeleton()
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun PatientAssignmentListItem(patient: Patient, checkedPatients: MutableList<String>) {
    val isChecked = remember { mutableStateOf(patient.id in checkedPatients) }

    PatientListItem(patient = patient, leadingContentPrefix = {
            Checkbox(
                modifier = Modifier.offset((-8).dp, 0.dp),
                checked = isChecked.value,
                onCheckedChange =  {
                    isChecked.value = it
                    if (isChecked.value) {
                        if (!checkedPatients.contains(patient.id))
                            checkedPatients.add(patient.id)
                    } else {
                        checkedPatients.remove(patient.id)
                    }
                }
            )
        }
    )
}

@Composable
fun ExerciseAssignmentListItem(exercise: Exercise, checkedExercises: MutableList<String>) {
    val isChecked = remember { mutableStateOf(exercise.id in checkedExercises) }

    ExerciseListItem(exercise = exercise, leadingContentPrefix = {
            Checkbox(
                modifier = Modifier.offset((-8).dp, 0.dp),
                checked = isChecked.value,
                onCheckedChange =  {
                    isChecked.value = it
                    if (isChecked.value) {
                        if (!checkedExercises.contains(exercise.id))
                            checkedExercises.add(exercise.id)
                    } else {
                        checkedExercises.remove(exercise.id)
                    }
                }
            )
        }
    )
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
            containerColor = MaterialTheme.colorScheme.secondary
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