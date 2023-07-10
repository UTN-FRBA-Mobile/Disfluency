package com.disfluency.screens.exercise

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.disfluency.R
import com.disfluency.data.ExerciseRepository
import com.disfluency.model.ExerciseAssignment
import com.disfluency.navigation.Route
import com.disfluency.screens.success.ON_SUCCESS_ANIMATION_TIME
import kotlinx.coroutines.*


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ExerciseRecordingScreen(assignmentId: String, navController: NavController){
    val assignment = remember { mutableStateOf<ExerciseAssignment?>(null) }

    LaunchedEffect(Unit) {
        val anAssignment = withContext(Dispatchers.IO) { ExerciseRepository.getAssignmentById(assignmentId) }
        Log.i("HTTP", anAssignment.toString())
        assignment.value = anAssignment
    }
    
    var animateVisibility by remember { mutableStateOf(true) }

    AnimatedVisibility(
        visible = animateVisibility,
        exit = scaleOut(animationSpec = tween(ON_SUCCESS_ANIMATION_TIME)) + fadeOut(animationSpec = tween(ON_SUCCESS_ANIMATION_TIME))
    ) {
        assignment.value?.let {
            RecordExercise(
                id = it.exercise.id,
                onSend = { file ->
                    CoroutineScope(Dispatchers.IO).launch {
                        ExerciseRepository.saveExercisePractice(assignmentId = assignmentId, audio = file)
                    }
                    animateVisibility = false
                },
                navController = navController
            )
        }
    }

    if (!animateVisibility){
        LaunchedEffect(key1 = true, block = {
            delay(ON_SUCCESS_ANIMATION_TIME.toLong())
            navController.popBackStack()
            navController.navigate(Route.PracticeSuccess.route)
        })
    }
}