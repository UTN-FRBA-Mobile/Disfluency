package com.disfluency.screens.exercise

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.disfluency.R
import com.disfluency.data.ExerciseRepository
import com.disfluency.loading.SkeletonLoader
import com.disfluency.loading.skeleton.exercise.ExercisePracticeDetailSkeleton
import com.disfluency.model.Exercise
import com.disfluency.model.ExerciseAssignment
import com.disfluency.model.ExercisePractice
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun ExercisePracticeDetailScreen(practiceId: String, assignmentId: String){
    val exerciseAssignment = remember { mutableStateOf<ExerciseAssignment?>(null) }

    LaunchedEffect(Unit) {
        val anExerciseAssignment = withContext(Dispatchers.IO) { ExerciseRepository.getAssignmentById(assignmentId) }
        Log.i("HTTP", anExerciseAssignment.toString())
        exerciseAssignment.value = anExerciseAssignment
    }

    SkeletonLoader(
        state = exerciseAssignment,
        content = {
            exerciseAssignment.value?.let {
                val practice = it.practiceAttempts.first { p -> p.id == practiceId }
                val exercise = it.exercise

                ExercisePracticeDetail(practice = practice, exercise = exercise)
            }
        },
        skeleton = {
            ExercisePracticeDetailSkeleton()
        }
    )


}

@Composable
fun ExercisePracticeDetail(practice: ExercisePractice, exercise: Exercise){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "${stringResource(R.string.exercise_answer_prefix)}${practice.date}",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(8.dp)
        )

        Text(
            text = exercise.title,
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            text = exercise.instruction,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(8.dp)
        )

        ExampleRecording(sampleAudioUrl = practice.recordingUrl)
    }
}