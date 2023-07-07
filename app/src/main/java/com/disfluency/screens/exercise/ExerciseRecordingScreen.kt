package com.disfluency.screens.exercise

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
import com.disfluency.navigation.Route
import kotlinx.coroutines.delay

const val ON_SUCCESS_ANIMATION_TIME = 300

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ExerciseRecordingScreen(assignmentId: String, navController: NavController){
    val assignment = ExerciseRepository.getAssignmentById(assignmentId)
    
    var animateVisibility by remember { mutableStateOf(true) }

    AnimatedVisibility(
        visible = animateVisibility,
        exit = scaleOut(animationSpec = tween(ON_SUCCESS_ANIMATION_TIME)) + fadeOut(animationSpec = tween(ON_SUCCESS_ANIMATION_TIME))
    ) {
        RecordExercise(
            id = assignment.exercise.id,
            onSend = { file ->
                ExerciseRepository.saveExercisePractice(assignmentId = assignmentId, audio = file)
                animateVisibility = false
            },
            navController = navController
        )
    }

    if (!animateVisibility){
        LaunchedEffect(key1 = true, block = {
            delay(ON_SUCCESS_ANIMATION_TIME.toLong())
            navController.popBackStack()
            navController.navigate(Route.PracticeSuccess.route)
        })
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RecordSuccessScreen(navController: NavController){
    val waitTime = 2000L

    var animateVisibility by remember { mutableStateOf(true) }

    LaunchedEffect(key1 = true, block = {
        delay(waitTime)
        animateVisibility = false
        delay((ON_SUCCESS_ANIMATION_TIME * 0.4).toLong())
        navController.popBackStack()
    })

    Box(modifier = Modifier.fillMaxSize()) {
        AnimatedVisibility(
            visible = animateVisibility,
            enter = fadeIn(tween(durationMillis = ON_SUCCESS_ANIMATION_TIME * 4, delayMillis = ON_SUCCESS_ANIMATION_TIME * 2))
                    + scaleIn(tween(durationMillis = ON_SUCCESS_ANIMATION_TIME * 4, delayMillis = ON_SUCCESS_ANIMATION_TIME * 2)),
            exit = fadeOut(tween(ON_SUCCESS_ANIMATION_TIME))
        ) {
            Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.primary){}
        }

        AnimatedVisibility(
            visible = animateVisibility,
            enter = fadeIn(tween(durationMillis = 1, delayMillis = ON_SUCCESS_ANIMATION_TIME)),
            exit = fadeOut(tween(ON_SUCCESS_ANIMATION_TIME))
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.CheckCircle,
                    contentDescription = "Done",
                    tint = Color.White,
                    modifier = Modifier
                        .size(100.dp)
                        .padding(16.dp)
                )
                Text(
                    text = stringResource(R.string.pa_record_success_message),
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}