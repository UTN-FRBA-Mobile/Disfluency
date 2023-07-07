package com.disfluency.screens.exercise

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.disfluency.R
import com.disfluency.data.ExerciseRepository

@Composable
fun ExercisePracticeDetailScreen(practiceId: String){
    val practice = ExerciseRepository.getPracticeById(id = practiceId)
    val exercise = ExerciseRepository.getExerciseDetailOfPractice(id = practiceId)

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