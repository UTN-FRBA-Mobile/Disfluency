package com.disfluency.screens.patient

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.disfluency.R
import com.disfluency.model.Patient
import com.disfluency.navigation.Route

@Composable
fun PatientExerciseAssignmentsScreen(navController: NavHostController, patient: Patient) {

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            contentPadding = PaddingValues(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(patient.exercises) {ex ->
                //TODO: Mejorar diseño
                ListItem(
                    headlineContent = { Text(text = ex.exercise.title) },
                    supportingContent = { Text(text = ex.dateOfAssignment.toString()) },
                    trailingContent = { Text(text = "${ex.attemptsCount()} ${stringResource(R.string.resoluciones)}") },
                    modifier = Modifier.clickable {
                        navController.navigate(Route.PatientExerciseAssignmentDetail.routeTo(ex.id))
                    }
                )
            }
        }
    }
}