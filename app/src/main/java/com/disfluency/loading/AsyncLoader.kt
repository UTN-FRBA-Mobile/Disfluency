package com.disfluency.loading

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color

val skeletonBackground = Color(0xFFCCCCCC)
val skeletonContent = Color(0xFFB6B6B6)
private const val FADE_TIME = 400

@Composable
fun <T>SkeletonLoader(state: MutableState<T?>, content:  @Composable() (() -> Unit), skeleton:  @Composable() (() -> Unit)){

    AnimatedVisibility(visible = state.value == null, exit = fadeOut(tween(FADE_TIME))) {
        skeleton.invoke()
    }

    AnimatedVisibility(visible = state.value != null, enter = fadeIn(tween(FADE_TIME, FADE_TIME))) {
        content.invoke()
    }
}