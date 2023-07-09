package com.disfluency.screens.patient

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.disfluency.R
import com.disfluency.model.ExerciseAssignment
import com.disfluency.model.Patient
import com.disfluency.navigation.Route
import com.disfluency.screens.exercise.ExerciseThumbnail
import java.time.format.DateTimeFormatter

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
                ExerciseAssignmentListItem(exerciseAssignment = ex, navController = navController)
            }
        }
    }
}

@Composable
fun ExerciseAssignmentListItem(exerciseAssignment: ExerciseAssignment, navController: NavHostController){
    val onClick = {
        navController.navigate(
            Route.PatientExerciseAssignmentDetail.routeTo(
                exerciseAssignment.id
            )
        )
    }

    Card(
        modifier = Modifier.clickable { onClick() },
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
    ) {
        ListItem(
            modifier = Modifier.height(56.dp),
            headlineContent = {
                Text(
                    text = exerciseAssignment.exercise.title,
                    style = MaterialTheme.typography.titleMedium
                )
            },
            supportingContent = {
                Text(
                    text = exerciseAssignment.dateOfAssignment.format(
                        DateTimeFormatter.ofPattern(stringResource(R.string.date_format))
                    ),
                    style = MaterialTheme.typography.labelMedium
                )
            },
            leadingContent = {
                ExerciseThumbnail(exercise = exerciseAssignment.exercise)
            },
            trailingContent = {
                Text(
                    text = "${exerciseAssignment.attemptsCount()} ${
                        stringResource(
                            R.string.resoluciones
                        )
                    }"
                )
            }
        )
    }
}