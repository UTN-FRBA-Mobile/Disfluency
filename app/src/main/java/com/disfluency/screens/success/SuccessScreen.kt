package com.disfluency.screens.success

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

const val ON_SUCCESS_ANIMATION_TIME = 300

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SuccessScreen(message: String, delay: Boolean = true, onSuccess: () -> Unit){
    val waitTime = 2000L

    var animateVisibility by remember { mutableStateOf(true) }

    LaunchedEffect(key1 = true, block = {
        delay(waitTime)
        animateVisibility = false
        delay((ON_SUCCESS_ANIMATION_TIME * 0.4).toLong())
        onSuccess()
    })

    Box(modifier = Modifier.fillMaxSize()) {
        AnimatedVisibility(
            visible = animateVisibility,
            enter = fadeIn(tween(durationMillis = ON_SUCCESS_ANIMATION_TIME * 4, delayMillis = if (delay) ON_SUCCESS_ANIMATION_TIME * 2 else 0))
                    + scaleIn(tween(durationMillis = ON_SUCCESS_ANIMATION_TIME * 4, delayMillis = if (delay) ON_SUCCESS_ANIMATION_TIME * 2 else 0)),
            exit = fadeOut(tween(ON_SUCCESS_ANIMATION_TIME))
        ) {
            Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.primary){}
        }

        AnimatedVisibility(
            visible = animateVisibility,
            enter = fadeIn(tween(durationMillis = 1, delayMillis = if (delay) ON_SUCCESS_ANIMATION_TIME else 0)),
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
                    text = message,
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

