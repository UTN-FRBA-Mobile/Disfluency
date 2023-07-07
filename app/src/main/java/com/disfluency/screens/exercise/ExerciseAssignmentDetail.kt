package com.disfluency.screens.exercise

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.disfluency.R
import com.disfluency.data.ExerciseRepository
import com.disfluency.model.ExercisePractice
import com.disfluency.navigation.Route
import com.disfluency.utils.millisecondsAsMinutesAndSeconds
import java.time.format.DateTimeFormatter
import kotlin.random.Random

@Composable
fun ExerciseAssignmentDetail(id: String, navController: NavController){
    val assignment = ExerciseRepository.getAssignmentById(id)
    //TODO: Temp
    Box(modifier = Modifier.fillMaxSize()){
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            SingleExerciseScreen(id = assignment.exercise.id)

            ExercisePracticeList(list = assignment.practiceAttempts, navController = navController)
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
            ) {
                Icon(Icons.Filled.Add, "Grabar")
            }
        }
    }
}

@Composable
fun ExercisePracticeList(list: List<ExercisePractice>, navController: NavController){
    Column(
        Modifier.padding(all = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        list.forEach { practice ->
            //TODO: Mejorar diseño
            ListItem(
                headlineContent = { Text(text = "${stringResource(R.string.exercise_answer_prefix)}${practice.date.format(DateTimeFormatter.ofPattern(stringResource(R.string.date_format)))}") },
                //TODO: temporal
                trailingContent = { Text(text = millisecondsAsMinutesAndSeconds(Random.nextLong(32000))) },
                modifier = Modifier
                    .clickable {
                        navController.navigate(Route.PatientExercisePracticeDetail.routeTo(practiceId = practice.id))
                    }
            )
        }
    }
}