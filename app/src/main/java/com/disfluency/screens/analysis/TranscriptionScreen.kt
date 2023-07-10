package com.disfluency.screens.analysis

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.disfluency.audio.playback.DisfluencyAudioPlayer
import com.disfluency.audio.playback.DisfluencyAudioUrlPlayer
import com.disfluency.components.audio.AudioPlayer
import com.disfluency.components.modifiers.verticalFadingEdge
import com.disfluency.data.MockedData
import com.disfluency.data.TranscriptionRepository
import com.disfluency.model.analysis.*
import com.disfluency.ui.theme.MyApplicationTheme

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

        Card(
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {

            FlowRow(
                Modifier
                    .padding(all = 8.dp)
                    .verticalFadingEdge(scrollState, length = 200.dp, edgeColor = MaterialTheme.colorScheme.surface)
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

}

@Composable
fun AnalysisRecording(url: String, audioPlayer: DisfluencyAudioPlayer){
    Column(Modifier.padding(horizontal = 8.dp)) {
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