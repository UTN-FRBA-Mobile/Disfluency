package com.disfluency.screens.exercise

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.disfluency.data.ExerciseRepository
import com.disfluency.screens.utils.EmptyScreen

@Composable
fun ExerciseRecordingScreen(assignmentId: String, navController: NavController){
    val assignment = ExerciseRepository.getAssignmentById(assignmentId)

    RecordExercise(
        id = assignment.exercise.id,
        onSend = { file -> ExerciseRepository.saveExercisePractice(assignmentId = assignmentId, audio = file) },
        navController = navController
    )
}