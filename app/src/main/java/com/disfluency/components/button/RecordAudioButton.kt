package com.disfluency.components.button

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Mic
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.core.math.MathUtils
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

const val MAX_SWIPE_BAR_HEIGHT = 170
const val ANIMATION_TIME = 200
const val DRAG_LIMIT = MAX_SWIPE_BAR_HEIGHT * 1.1f
const val DRAG_SEGMENT = (DRAG_LIMIT / 3) * 1.3

//TODO: probar como se ve horizontal, el deslizar dejarlo igual,
// pero cuando bloqueo, que se vaya lo de deslizar y solo aparezcan el boton de enviar y el de cancelar (botones de verdad no desliz)

@Composable
fun RecordSwipeButton(
    onClick: () -> Unit,
    onRelease: () -> Unit,
    onSend: () -> Unit, //TODO: hace falta este o es lo mismo que onRelease?
    onCancel: () -> Unit,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {

    val isPressed by interactionSource.collectIsPressedAsState()
    var isDragged by remember { mutableStateOf(false) }
    val isInteracting = isPressed || isDragged

    var offsetY by remember { mutableStateOf(0f) }
    val currentStage = calculateStageFromOffset(offsetY)

    val animatedButtonColor = animateColorAsState(
        targetValue = if (isInteracting) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.secondary,
        animationSpec = tween(ANIMATION_TIME, 0, LinearEasing)
    )

    val animateSize = animateDpAsState(
        targetValue = if (isInteracting && offsetY.absoluteValue < DRAG_SEGMENT) 75.dp else 50.dp,
        animationSpec = tween(ANIMATION_TIME, 0, LinearEasing)
    )

    val animateBackgroundBarHeight = animateDpAsState(
        targetValue = if (isInteracting) MAX_SWIPE_BAR_HEIGHT.dp else 0.dp,
        animationSpec = tween(ANIMATION_TIME, ANIMATION_TIME / 2, LinearEasing)
    )

    var actionOnce by remember { mutableStateOf(false) }

    val submit = {
        actionOnce = false
        offsetY = 0f
    }

    Box(
        modifier = Modifier.height(240.dp),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier.size(
                width = 50.dp,
                height = animateBackgroundBarHeight.value
            ),
            color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.3f),
            shape = RoundedCornerShape(25.dp)
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Icon(imageVector = Icons.Outlined.Lock, contentDescription = "Lock", tint = MaterialTheme.colorScheme.secondary)
                Icon(imageVector = Icons.Outlined.Cancel, contentDescription = "Cancel", tint = MaterialTheme.colorScheme.secondary)
            }
        }

        Button(
            onClick = {
                submit()
                onSend()
            },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
            contentPadding = PaddingValues(1.dp),
            modifier = modifier
                .size(50.dp)
                .alpha(-offsetY / DRAG_LIMIT)
        ){
            Icon(imageVector = Icons.Outlined.Send, contentDescription = "Send", tint = MaterialTheme.colorScheme.onSecondaryContainer)
        }

        Button(
            onClick = { },
            colors = ButtonDefaults.buttonColors(containerColor = animatedButtonColor.value),
            contentPadding = PaddingValues(1.dp),
            modifier = modifier
                .size(animateSize.value)
                .offset { IntOffset(0, offsetY.roundToInt()) }
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = { isDragged = true },
                        onDragEnd = {
                            //End drag only if button was not locked
                            if (offsetY > -DRAG_LIMIT) isDragged = false
                        }
                    ) { change, dragAmount ->
                        change.consume()

                        offsetY = MathUtils.clamp(offsetY + dragAmount.y, -DRAG_LIMIT, DRAG_LIMIT)
                    }
                },
            interactionSource = interactionSource,
            border = BorderStroke(2.dp, MaterialTheme.colorScheme.secondary)
        ) {
            if (isInteracting) {

                if (!actionOnce){
                    onClick()
                    actionOnce = true
                }

                //Use if + DisposableEffect to wait for the press action is completed
                DisposableEffect(Unit) {
                    onDispose {

                        submit()

                        when(calculateStageFromOffset(offsetY)){
                            RecordButtonStage.RECORD -> onRelease()
                            RecordButtonStage.CANCEL -> onCancel()
                            else -> {}
                        }
                    }
                }
            }

            CrossfadeIcon(
                targetState = currentStage,
                iconConditionPairs = listOf(
                    Pair(Icons.Outlined.Mic, RecordButtonStage.RECORD),
                    Pair(Icons.Outlined.Cancel, RecordButtonStage.CANCEL),
                    Pair(Icons.Outlined.Lock, RecordButtonStage.LOCK)
                )
            )
        }
    }
}

private enum class RecordButtonStage(val enabledOnOffset: (Float) -> Boolean) {
    RECORD({ offset -> offset.absoluteValue < DRAG_SEGMENT }),
    LOCK({ offset -> offset < -DRAG_SEGMENT }),
    CANCEL({ offset -> offset > DRAG_SEGMENT }),
    SEND({ false })
}

private fun calculateStageFromOffset(offset: Float): RecordButtonStage {
    return RecordButtonStage.values().first { it.enabledOnOffset(offset) }
}

@Composable
fun <T> CrossfadeIcon(targetState: T, iconConditionPairs: List<Pair<ImageVector, T>>){
    Box {
        Crossfade(targetState = targetState, animationSpec = tween(ANIMATION_TIME)) { value ->

            iconConditionPairs.forEach {
                if (it.second == value){
                    Icon(imageVector = it.first, contentDescription = "Icon", tint = MaterialTheme.colorScheme.onSecondaryContainer)
                }
            }
        }
    }
}