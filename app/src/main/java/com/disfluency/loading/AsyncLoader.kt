package com.disfluency.loading

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.shimmer

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