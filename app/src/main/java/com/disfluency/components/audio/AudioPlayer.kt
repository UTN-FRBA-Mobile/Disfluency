package com.disfluency.components.audio

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.disfluency.audio.DisfluencyAudioPlayer
import com.disfluency.utils.millisecondsAsMinutesAndSeconds

@Composable
fun AudioPlayer(url: String){
    val ctx = LocalContext.current
    val mediaPlayer = DisfluencyAudioPlayer(ctx)
    mediaPlayer.loadUrl(url)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .clip(RoundedCornerShape(10.dp)),
        color = MaterialTheme.colorScheme.surfaceTint
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                modifier = Modifier.size(40.dp),
                contentPadding = PaddingValues(1.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                onClick = {
                    if (!mediaPlayer.isPlaying()){
                        mediaPlayer.play()
                    } else {
                        mediaPlayer.pause()
                    }
                },
                enabled = mediaPlayer.asyncReady()
            ) {
                if (mediaPlayer.isPlaying())
                    Icon(imageVector = Icons.Filled.Pause, contentDescription = "Pause")
                else
                    Icon(imageVector = Icons.Filled.PlayArrow, contentDescription = "Play")
            }

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Slider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                        .clip(CircleShape),
                    value = mediaPlayer.position().toFloat(),
                    valueRange = 0f..mediaPlayer.duration().toFloat(),
                    onValueChange = { sliderValue ->
                        mediaPlayer.seekTo(sliderValue.toInt())
                    },
                    colors = SliderDefaults.colors(
                        thumbColor = MaterialTheme.colorScheme.primary,
                        activeTrackColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        inactiveTrackColor = MaterialTheme.colorScheme.surfaceVariant
                    ),
                    thumb = {
                        SliderDefaults.Thumb(
                            interactionSource = MutableInteractionSource(),
                            modifier = Modifier.offset(x = 5.dp, y = 5.5.dp),
                            thumbSize = DpSize(10.dp,10.dp)
                        )
                    }
                )
                
                Text(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .wrapContentSize(align = Alignment.BottomStart),
                    text = millisecondsAsMinutesAndSeconds(mediaPlayer.position().toLong()),
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray,
                )
            }
            
        }
    }
}

