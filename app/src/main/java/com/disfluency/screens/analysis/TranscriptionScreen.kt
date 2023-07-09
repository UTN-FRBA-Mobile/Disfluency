package com.disfluency.screens.analysis

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.disfluency.audio.playback.DisfluencyAudioPlayer
import com.disfluency.audio.playback.DisfluencyAudioUrlPlayer
import com.disfluency.components.audio.AudioMediaType
import com.disfluency.components.audio.AudioPlayer
import com.disfluency.data.MockedData
import com.disfluency.data.TranscriptionRepository
import com.disfluency.model.analysis.*
import com.disfluency.ui.theme.MyApplicationTheme
import java.lang.Float.min

@Composable
fun TranscriptionScreen(practiceId: String) {
    //TODO: sacar mockeo
    val analysis = TranscriptionRepository.mockedTranscriptions[0]

    val disfluencyAudioPlayer = DisfluencyAudioUrlPlayer(LocalContext.current)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Box(modifier = Modifier
            .wrapContentSize()
            .weight(0.8f)){
            Transcription(analysis, disfluencyAudioPlayer)
        }

        Box(modifier = Modifier
            .wrapContentSize()
            .weight(0.2f)){
            AnalysisRecording(url = MockedData.testUrl, audioPlayer = disfluencyAudioPlayer)
        }

    }
}

@Composable
@OptIn(ExperimentalLayoutApi::class)
private fun Transcription(analysis: Analysis, disfluencyAudioPlayer: DisfluencyAudioPlayer) {
    Column() {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            text = "Transcripcion del ejercicio",
            style = MaterialTheme.typography.headlineSmall
        )

        val scrollState = rememberScrollState()

        FlowRow(
            Modifier
                .padding(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 16.dp)
                .verticalFadingEdge(scrollState, length = 200.dp, edgeColor = Color.White)
                .verticalScroll(scrollState)
        ) {

            analysis.analysedWords.forEach { word ->
                Column(
                    modifier = Modifier.wrapContentWidth()
                ) {
                    Surface(
                        shape = MaterialTheme.shapes.large,
                        color = if (word.getDisfluency().isEmpty()) Color.Transparent else
                            MaterialTheme.colorScheme.secondaryContainer
                    ) {
                        Text(
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            text = word.getDisfluency(),
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }

                    val bgColor: Color by animateColorAsState(
                        targetValue = if (word.isTimeInBetween(disfluencyAudioPlayer.position())) MaterialTheme.colorScheme.primary else Color.Black,
                        animationSpec = tween(50, 0, LinearEasing)
                    )

                    Text(
                        text = word.word + " ",
                        fontSize = 18.sp,
                        modifier = Modifier.clickable { disfluencyAudioPlayer.seekTo(word.startTime) },
                        color = bgColor
                    )

                    // Se arregla en androidx.compose.foundation:foundation-layout:1.5.0 que agrega al FlowRow un VerticalArrangement
                    Spacer(modifier = Modifier.height(2.dp))
                }
            }
        }
    }

}

@Composable
fun AnalysisRecording(url: String, audioPlayer: DisfluencyAudioPlayer){
    Column() {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            text = "Audio Analizado",
            style = MaterialTheme.typography.labelMedium
        )

        AudioPlayer(url = url, audioPlayer = audioPlayer)
    }
}

@Preview(showBackground = true)
@Composable
fun TranscriptionPreview() {
    MyApplicationTheme {
        Column(Modifier.padding(vertical = 20.dp)) {
            TranscriptionScreen(practiceId = "1")
        }

    }
}










fun Modifier.verticalFadingEdge(
    scrollState: ScrollState,
    length: Dp,
    edgeColor: Color? = null,
) = composed(
    debugInspectorInfo {
        name = "length"
        value = length
    }
) {
    val color = edgeColor ?: MaterialTheme.colorScheme.surface

    drawWithContent {
        val lengthValue = length.toPx()
        val scrollFromTop by derivedStateOf { scrollState.value }
        val scrollFromBottom by derivedStateOf { scrollState.maxValue - scrollState.value }

        val topFadingEdgeStrength = lengthValue * (scrollFromTop / lengthValue).coerceAtMost(1f)

        val bottomFadingEdgeStrength = lengthValue * (scrollFromBottom / lengthValue).coerceAtMost(1f)

        drawContent()

        drawRect(
            brush = Brush.verticalGradient(
                colors = listOf(
                    color,
                    Color.Transparent,
                ),
                startY = 0f,
                endY = topFadingEdgeStrength,
            ),
            size = Size(
                this.size.width,
                topFadingEdgeStrength
            ),
        )

        drawRect(
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color.Transparent,
                    color,
                ),
                startY = size.height - bottomFadingEdgeStrength,
                endY = size.height,
            ),
            topLeft = Offset(x = 0f, y = size.height - bottomFadingEdgeStrength),
        )
    }
}