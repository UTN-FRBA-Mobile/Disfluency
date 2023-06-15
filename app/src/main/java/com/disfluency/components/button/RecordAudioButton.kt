package com.disfluency.components.button

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

//TODO: queda que al animar el boton grande, que se anime la aparicion del boton de lock y el boton de borrar

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