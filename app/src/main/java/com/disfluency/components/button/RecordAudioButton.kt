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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.core.math.MathUtils
import kotlin.math.absoluteValue
import kotlin.math.roundToInt
import kotlin.math.sign


@Composable
fun PressAndReleaseButton(
    onClick: () -> Unit,
    onRelease: () -> Unit,
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource =
        remember { MutableInteractionSource() },
) {
    val isPressed by interactionSource.collectIsPressedAsState()

    val animatedButtonColor = animateColorAsState(
        targetValue = if (isPressed) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.secondary,
        animationSpec = tween(200, 0, LinearEasing)
    )

    val animateSize = animateDpAsState(
        targetValue = if (isPressed) 100.dp else 50.dp,
        animationSpec = tween(200, 0, LinearEasing)
    )

    var actionOnce by remember { mutableStateOf(false) }

    Button(
        onClick = { },
        colors = ButtonDefaults.buttonColors(containerColor = animatedButtonColor.value),
        contentPadding = PaddingValues(1.dp),
        modifier = modifier.size(animateSize.value),
        interactionSource = interactionSource,
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.secondary)
    ) {
        if (isPressed) {

            if (!actionOnce){
                onClick()
                actionOnce = true
            }

            //Use if + DisposableEffect to wait for the press action is completed
            DisposableEffect(Unit) {
                onDispose {
                    //released
                    onRelease()
                    actionOnce = false
                }
            }

        }

        content()
    }
}


const val MAX_SWIPE_BAR_HEIGHT = 170
const val ANIMATION_TIME = 200


@Composable
fun RecordSwipeButton(
    onClick: () -> Unit,
    onRelease: () -> Unit,
    onSend: () -> Unit,
    onCancel: () -> Unit, //TODO: agregar el callback este cuando suelto abajo
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {

    val isPressed by interactionSource.collectIsPressedAsState()
    var isDragged by remember { mutableStateOf(false) }
    val isInteracting = isPressed || isDragged

    var offsetY by remember { mutableStateOf(0f) }

    val maxDraggedValue = MAX_SWIPE_BAR_HEIGHT * 1.1f
    val stage = (maxDraggedValue / 3) * 1.3

    val animatedButtonColor = animateColorAsState(
        targetValue = if (isInteracting) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.secondary,
        animationSpec = tween(ANIMATION_TIME, 0, LinearEasing)
    )

    val animateSize = animateDpAsState(
        targetValue = if (isInteracting && offsetY.absoluteValue < stage) 75.dp else 50.dp,
        animationSpec = tween(ANIMATION_TIME, 0, LinearEasing)
    )

    val animateBackgroundBarHeight = animateDpAsState(
        targetValue = if (isInteracting) MAX_SWIPE_BAR_HEIGHT.dp else 0.dp,
        animationSpec = tween(ANIMATION_TIME, ANIMATION_TIME / 2, LinearEasing)
    )

    var actionOnce by remember { mutableStateOf(false) }

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
                onSend()
            },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
            contentPadding = PaddingValues(1.dp),
            modifier = modifier.size(50.dp).alpha(-offsetY / maxDraggedValue)
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
                            if (offsetY > -maxDraggedValue) isDragged = false

                        }
                    ) { change, dragAmount ->
                        change.consume()

                        offsetY = MathUtils.clamp(offsetY + dragAmount.y, -maxDraggedValue, maxDraggedValue)
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
                        //released
                        onRelease()
                        actionOnce = false
                        offsetY = 0f
                    }
                }
            }

            Box {
                Crossfade(targetState = offsetY, animationSpec = tween(ANIMATION_TIME / 4)) { offset ->

                    if (offset.absoluteValue < stage) {
                        Icon(imageVector = Icons.Outlined.Mic, contentDescription = "Record", tint = MaterialTheme.colorScheme.onSecondaryContainer)
                    } else if (offset > stage){
                        Icon(imageVector = Icons.Outlined.Cancel, contentDescription = "Cancel", tint = MaterialTheme.colorScheme.onSecondaryContainer)
                    } else if (offset < -stage){
                        Icon(imageVector = Icons.Outlined.Lock, contentDescription = "Lock", tint = MaterialTheme.colorScheme.onSecondaryContainer)
                    }
                }
            }
        }
    }
}

//@Composable
//fun CrossfadeRecordIcon(){
//    Box {
//        Crossfade(targetState = offsetY, animationSpec = tween(ANIMATION_TIME / 4)) { offset ->
//
//            if (offset.absoluteValue < stage) {
//                Icon(imageVector = Icons.Outlined.Mic, contentDescription = "Record", tint = MaterialTheme.colorScheme.onSecondaryContainer)
//            } else if (offset > stage){
//                Icon(imageVector = Icons.Outlined.Cancel, contentDescription = "Cancel", tint = MaterialTheme.colorScheme.onSecondaryContainer)
//            } else if (offset < -stage){
//                Icon(imageVector = Icons.Outlined.Lock, contentDescription = "Lock", tint = MaterialTheme.colorScheme.onSecondaryContainer)
//            }
//        }
//    }
//}