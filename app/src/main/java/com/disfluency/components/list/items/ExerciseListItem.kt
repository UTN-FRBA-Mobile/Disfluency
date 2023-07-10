package com.disfluency.components.list.items

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.disfluency.model.Exercise

@Composable
fun ExerciseListItem(exercise: Exercise, onClick: () -> Unit = {}, leadingContentPrefix: @Composable () -> Unit = {}) {
    Card(
        modifier = Modifier.clickable { onClick() },
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
    ){
        ListItem(
            modifier = Modifier.height(56.dp),
            headlineContent = {
                Text(
                    text = exercise.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            supportingContent = {
                Text(
                    text = exercise.getFullInstructions(),
                    maxLines = 1,
                    style = MaterialTheme.typography.labelMedium,
                    overflow = TextOverflow.Ellipsis
                )
            },
            leadingContent = {
                Row {
                    leadingContentPrefix()
                    ExerciseThumbnail(exercise = exercise)
                }
            })
    }
}

@Composable
fun ExerciseThumbnail(exercise: Exercise){
    val color = stringToRGB(exercise.title)
    Surface(
        color = color,
        modifier = Modifier
            .clip(CircleShape)
            .size(40.dp)
            .border(
                1.5.dp,
                color
                    .copy(0.5f)
                    .compositeOver(Color.Black),
                CircleShape
            ),
    ) {
        Box(contentAlignment = Alignment.Center){
            Text(
                text = exercise.title.first().uppercaseChar().toString(),
                style = TextStyle(color = Color.White, fontSize = 18.sp)
            )
        }
    }
}


//TODO: mover a otro lado esto!
fun stringToRGB(string: String): Color {
    val i = string.hashCode()

    val a = (i shr 24 and 0xFF)
    val r = (i shr 16 and 0xFF)
    val g = (i shr 8 and 0xFF)
    val b = (i and 0xFF)
    return Color(r, g, b, 255);
}