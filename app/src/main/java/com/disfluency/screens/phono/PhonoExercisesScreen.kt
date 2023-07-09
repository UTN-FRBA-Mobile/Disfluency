package com.disfluency.screens.phono

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.disfluency.data.ExerciseRepository
import com.disfluency.data.PatientRepository
import com.disfluency.model.ExerciseAssignment
import com.disfluency.model.Patient
import com.disfluency.navigation.Route
import com.disfluency.screens.exercise.ExercisePracticeDetailScreen
import com.disfluency.screens.exercise.ExercisePracticeList
import com.disfluency.screens.exercise.SingleExerciseScreen
import com.disfluency.screens.patient.PatientExerciseAssignmentsScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun PhonoExercisesScreen(id: String, navController: NavHostController) {
    val patient = remember { mutableStateOf<Patient?>(null) }

    LaunchedEffect(Unit) {
        val aPatient = withContext(Dispatchers.IO) { PatientRepository.getPatientById(id) }
        Log.i("HTTP", aPatient.toString())
        patient.value = aPatient
    }

    patient.value?.let { PatientExerciseAssignmentsScreen(navController = navController, patientId = it.id) }
}

@Composable
fun TherapistExerciseAssignmentDetail(id: String, navController: NavController){
    val assignment = remember { mutableStateOf<ExerciseAssignment?>(null) }

    LaunchedEffect(Unit) {
        val anAssignment = withContext(Dispatchers.IO) { ExerciseRepository.getAssignmentById(id) }
        Log.i("HTTP", anAssignment.toString())
        assignment.value = anAssignment
    }

    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        assignment.value?.let {
            SingleExerciseScreen(id = it.exercise.id)

            ExercisePracticeList(assignment = it, navController = navController)
        }
    }
}

@Composable
fun TherapistExercisePracticeDetail(id: String, assignmentId: String, navController: NavController){
    Box(modifier = Modifier.fillMaxSize()){
        ExercisePracticeDetailScreen(practiceId = id, assignmentId = assignmentId)

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomEnd
        ) {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Route.TranscriptionAnalysis.routeTo(id))
                },
                modifier = Modifier.padding(16.dp),
                containerColor = MaterialTheme.colorScheme.secondary
            ) {
                Icon(Icons.Filled.Analytics, "Analisis")
            }
        }
    }
}