package com.disfluency.screens.analysis

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.disfluency.audio.DisfluencyAudioPlayer
import com.disfluency.components.audio.AudioPlayer
import com.disfluency.components.user.IconLabeledDetails
import com.disfluency.components.user.PatientInfoCard
import com.disfluency.data.MockedData
import com.disfluency.data.TranscriptionRepository
import com.disfluency.model.analysis.*
import com.disfluency.ui.theme.MyApplicationTheme

@Composable
fun TranscriptionScreen() {
    //TODO: sacar mockeo
    val patient = MockedData.therapists.first().patients.first()
    val analysis = TranscriptionRepository.mockedTranscriptions[0]

    val disfluencyAudioPlayer = DisfluencyAudioPlayer(LocalContext.current)
    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
//        PatientInfoCard(
//            patient = patient,
//            firstLabel = IconLabeledDetails(
//                Icons.Outlined.Person,
//                patient.age().toString(),
//                "Analysis"
//            ),
//            secondLabel = IconLabeledDetails(Icons.Outlined.CalendarMonth, "22/06/23", "Date")
//        )
        TitleText(title = "Transcripción")
        Transcription(analysis, disfluencyAudioPlayer)
        Spacer(modifier = Modifier.height(32.dp))
        TitleText("Audio")
        AudioPlayer(url = MockedData.testUrl, audioPlayer = disfluencyAudioPlayer)
    }
}

@Composable
private fun TitleText(title: String) {
    Text(
        text = title,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        modifier = Modifier.padding(8.dp)
    )
}

@Composable
@OptIn(ExperimentalLayoutApi::class)
private fun Transcription(analysis: Analysis, disfluencyAudioPlayer: DisfluencyAudioPlayer) {
    FlowRow(Modifier.padding(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 16.dp)) {
        analysis.analysedWords.forEach { word ->
            Column {
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
//                    color = lerp(Color.Black, MaterialTheme.colorScheme.primary, if(disfluencyAudioPlayer.duration() == 0) 0f else (word.startTime / disfluencyAudioPlayer.duration()).toFloat())
                    color = bgColor
                )

                // Se arregla en androidx.compose.foundation:foundation-layout:1.5.0 que agrega al FlowRow un VerticalArrangement
                Spacer(modifier = Modifier.height(2.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TranscriptionPreview() {
    MyApplicationTheme {
        TranscriptionScreen()
    }
}