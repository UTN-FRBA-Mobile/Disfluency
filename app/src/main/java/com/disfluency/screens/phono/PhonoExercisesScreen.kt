package com.disfluency.screens.phono

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.disfluency.data.ExerciseRepository
import com.disfluency.data.PatientRepository
import com.disfluency.navigation.Route
import com.disfluency.screens.exercise.ExercisePracticeDetailScreen
import com.disfluency.screens.exercise.ExercisePracticeList
import com.disfluency.screens.exercise.SingleExerciseScreen
import com.disfluency.screens.patient.PatientExerciseAssignmentsScreen

@Composable
fun PhonoExercisesScreen(id: Int, navController: NavHostController) {
    val patient = PatientRepository.getPatientById(id)

    PatientExerciseAssignmentsScreen(navController = navController, patient = patient)
}

@Composable
fun TherapistExerciseAssignmentDetail(id: String, navController: NavController){
    val assignment = ExerciseRepository.getAssignmentById(id)

    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        SingleExerciseScreen(id = assignment.exercise.id)

        ExercisePracticeList(list = assignment.practiceAttempts, navController = navController)
    }
}

@Composable
fun TherapistExercisePracticeDetail(id: String, navController: NavController){
    Box(modifier = Modifier.fillMaxSize()){
        ExercisePracticeDetailScreen(practiceId = id)

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