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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.core.math.MathUtils
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

const val MAX_SWIPE_BAR_WIDTH = 220
const val ANIMATION_TIME = 200
const val DRAG_LIMIT = MAX_SWIPE_BAR_WIDTH * 1.1f
//const val DRAG_SEGMENT = (DRAG_LIMIT / 3) * 1.3
const val DRAG_SEGMENT = DRAG_LIMIT * 0.9f

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

    var offsetX by remember { mutableStateOf(0f) }
    val currentStage = calculateStageFromOffset(offsetX)

    val animatedButtonColor = animateColorAsState(
        targetValue = if (isInteracting) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.secondary,
        animationSpec = tween(ANIMATION_TIME, 0, LinearEasing)
    )

    val animateSize = animateDpAsState(
        targetValue = if (isInteracting && offsetX.absoluteValue < DRAG_SEGMENT) 75.dp else 50.dp,
        animationSpec = tween(ANIMATION_TIME, 0, LinearEasing)
    )

    val animateBackgroundBarHeight = animateDpAsState(
        targetValue = if (isInteracting) MAX_SWIPE_BAR_WIDTH.dp else 0.dp,
        animationSpec = tween(ANIMATION_TIME, ANIMATION_TIME / 2, LinearEasing)
    )

    var actionOnce by remember { mutableStateOf(false) }
    var locked by remember { mutableStateOf(false) }

    val reset = {
        actionOnce = false
        locked = false
        isDragged = false
        offsetX = 0f

        println("Interacting: " + isInteracting)
        println("Pressed: " + isPressed)
        println("Dragged: " + isDragged)
    }


    //TODO: Boton de borrar una vez que solte o envie
//    Button(
//        modifier = Modifier.size(50.dp),
//        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
//        border = BorderStroke(2.dp, Color.Red),
//        contentPadding = PaddingValues(1.dp),
//        onClick = { /*TODO*/ }
//    ) {
//        Icon(imageVector = Icons.Outlined.Delete, contentDescription = "Delete", tint = Color.Red)
//    }

    Box(
        modifier = Modifier
            .height(75.dp)
            .width(240.dp),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier
                .size(
                    width = animateBackgroundBarHeight.value,
                    height = 50.dp
                ),
//                .alpha(1f - (offsetX / DRAG_LIMIT) / 2f),
            color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.3f),
            shape = RoundedCornerShape(25.dp)
        ){
            Row(
                modifier = Modifier
                    .fillMaxSize(),
//                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (locked){
                    Button(
                        onClick = {
                            onCancel()
                            reset()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                        border = BorderStroke(2.dp, MaterialTheme.colorScheme.secondary),
                        contentPadding = PaddingValues(1.dp),
                        modifier = modifier
                            .size(50.dp)
                    ){
                        Icon(imageVector = Icons.Outlined.Cancel, contentDescription = "Cancel", tint = MaterialTheme.colorScheme.onSecondaryContainer)
                    }
                } else {
                    Icon(
                        imageVector = Icons.Outlined.Cancel,
                        contentDescription = "Cancel",
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.padding(start = 14.dp)
                    )
                }
                Icon(
                    imageVector = Icons.Outlined.Lock,
                    contentDescription = "Lock",
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(end = 12.dp)
                )
            }
        }


        if(locked){
            Button(
                onClick = {
//                submit()
                    onSend()
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                border = BorderStroke(2.dp, MaterialTheme.colorScheme.secondary),
                contentPadding = PaddingValues(1.dp),
                modifier = modifier
                    .size(50.dp)
//                .alpha(offsetX / DRAG_LIMIT)
            ){
                Icon(imageVector = Icons.Outlined.Send, contentDescription = "Send", tint = MaterialTheme.colorScheme.onSecondaryContainer)
            }
        }




        Button(
            onClick = { },
            colors = ButtonDefaults.buttonColors(containerColor = animatedButtonColor.value),
            contentPadding = PaddingValues(1.dp),
            modifier = modifier
                .size(animateSize.value)
                .offset { IntOffset(offsetX.roundToInt(), 0) }
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = { isDragged = true },
                        onDragEnd = {
                            //End drag only if button was not locked
                            if (!RecordButtonStage.LOCK.enabledOnOffset(offsetX)) isDragged = false
                            else locked = true
                        }
                    ) { change, dragAmount ->
                        change.consume()

                        if (!locked)
                            offsetX =
                                MathUtils.clamp(offsetX + dragAmount.x, -DRAG_LIMIT, DRAG_LIMIT)
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

                        if (isInteracting) {
                            println("Dispose")
                            println(offsetX)

                            when(calculateStageFromOffset(offsetX)){
                                RecordButtonStage.RECORD -> onRelease()
                                RecordButtonStage.CANCEL -> onCancel()
                                else -> {}
                            }
                        }

                        reset()
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
    CANCEL({ offset -> offset < -DRAG_SEGMENT }),
    LOCK({ offset -> offset > DRAG_SEGMENT }),
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