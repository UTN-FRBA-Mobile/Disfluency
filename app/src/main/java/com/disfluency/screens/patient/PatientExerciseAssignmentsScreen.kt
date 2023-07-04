package com.disfluency.screens.patient

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.disfluency.model.Patient

@Composable
fun PatientExerciseAssignmentsScreen(navController: NavHostController, patient: Patient) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        //TODO: Distinguir si esta resuelto y agregar fecha.
//        ExerciseList(patient.exercises.reversed(), navController, "")
    }
}