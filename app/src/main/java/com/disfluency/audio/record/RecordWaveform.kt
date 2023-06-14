package com.disfluency.audio.record

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp


@Composable
fun LiveWaveform(amplitudes: MutableList<Float>, maxSpikes: Int, maxHeight: Float){
    val color = MaterialTheme.colorScheme.secondary

    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(24.dp)
        .drawWithCache {
            onDrawWithContent {
                amplitudes
                    .takeLast(maxSpikes)
                    .forEachIndexed { index, amp ->

                        val x = index * (size.width / maxSpikes)
                        val length = maxHeight * amp
                        val y = size.center.y - length/2

                        if (amp > 0)
                            drawLine(
                                color = color,
                                start = Offset(x = x, y = y),
                                end = Offset(x = x, y = y + length),
                                strokeWidth = 15f,
                                cap = StrokeCap.Round
                            )
                    }
            }
        })
}