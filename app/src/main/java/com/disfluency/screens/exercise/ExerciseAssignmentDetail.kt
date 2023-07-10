package com.disfluency.screens.exercise

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.disfluency.R
import com.disfluency.data.ExerciseRepository
import com.disfluency.model.ExerciseAssignment
import com.disfluency.navigation.Route
import com.disfluency.utils.millisecondsAsMinutesAndSeconds
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.format.DateTimeFormatter
import kotlin.random.Random

@Composable
fun ExerciseAssignmentDetail(id: String, navController: NavController){
    val assignment = remember { mutableStateOf<ExerciseAssignment?>(null) }

    LaunchedEffect(Unit) {
        val anAssignment = withContext(Dispatchers.IO) { ExerciseRepository.getAssignmentById(id) }
        Log.i("HTTP", anAssignment.toString())
        assignment.value = anAssignment
    }

    //TODO: Temp
    Box(modifier = Modifier.fillMaxSize()){
        assignment.value?.let {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                SingleExerciseScreen(id = it.exercise.id)

                ExercisePracticeList(assignment = it, navController = navController)
            }

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomEnd
            ) {
                FloatingActionButton(
                    onClick = {
                        navController.navigate(Route.PatientExerciseRecordPractice.routeTo(id))
                    },
                    modifier = Modifier.padding(16.dp),
                    containerColor = MaterialTheme.colorScheme.secondary
                ) {
                    Icon(Icons.Filled.Add, "Grabar")
                }
            }
        }
    }
}

@Composable
fun ExercisePracticeList(assignment: ExerciseAssignment, navController: NavController){
    Column(
        Modifier.padding(all = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        assignment.practiceAttempts.forEach { practica ->
            //TODO: Mejorar diseño
            ListItem(
                headlineContent = { Text(text = "${stringResource(R.string.exercise_answer_prefix)}${practica.date.format(DateTimeFormatter.ofPattern(stringResource(R.string.date_format)))}") },
                //TODO: temporal
                trailingContent = { Text(text = millisecondsAsMinutesAndSeconds(Random.nextLong(32000))) },
                modifier = Modifier
                    .clickable {
                        navController.navigate(Route.PatientExercisePracticeDetail.routeTo(
                            assignmentId = assignment.id, practiceId = practica.id))
                    }
            )
        }
    }
}