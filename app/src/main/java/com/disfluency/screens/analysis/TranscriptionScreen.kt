package com.disfluency.screens.analysis

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.disfluency.model.analysis.*
import com.disfluency.ui.theme.MyApplicationTheme

@Composable
fun TranscriptionScreen() {
    val patient = MockedData.therapists.first().patients.first()
    val disfluencyType = DisfluencyType.V
    val disfluencyType2 = DisfluencyType.M
    val disfluency = SingleDisfluency(disfluencyType)
    val compositeDisfluency = CompositeDisfluency(listOf(disfluencyType, disfluencyType2))
    val analysis = Analysis(
        listOf(
            AnalysedWord("Me", disfluency, 0, 2000),
            AnalysedWord("parece", disfluency, 2000, 3000),
            AnalysedWord("que",startTime = 3000, endTime = 5000),
            AnalysedWord("esto", compositeDisfluency, 5000, 7000),
            AnalysedWord("no", startTime = 7000, endTime = 10000),
            AnalysedWord("funciona", disfluency, 10000, 12000),
            AnalysedWord("muy", compositeDisfluency, 12000, 13000),
            AnalysedWord("bien", startTime = 13000, endTime = 15000),
            AnalysedWord("ah", disfluency, 15000, 18000),
            AnalysedWord("para!", disfluency, 18000, 19000),
            AnalysedWord("re", startTime = 19000, endTime = 20000),
            AnalysedWord("funciona", compositeDisfluency, 20000, 25000),
            AnalysedWord("ahora", startTime = 25000, endTime = 27000),
            AnalysedWord("nice", disfluency, 27000, 30000),
            AnalysedWord("murcielago", compositeDisfluency, 30000, 35000),
            AnalysedWord("chau", startTime = 35000, endTime = 40000)
        )
    )
    val disfluencyAudioPlayer = DisfluencyAudioPlayer(LocalContext.current)
    Column(modifier = Modifier.fillMaxWidth()) {
        PatientInfoCard(
            patient = patient,
            firstLabel = IconLabeledDetails(
                Icons.Outlined.Person,
                patient.age().toString(),
                "Analysis"
            ),
            secondLabel = IconLabeledDetails(Icons.Outlined.CalendarMonth, "22/06/23", "Date")
        )
        TitleText(title = "Transcripción")
        Transcription(analysis, disfluencyAudioPlayer)
        Spacer(modifier = Modifier.height(32.dp))
        TitleText("Audio")
        Box(Modifier.padding(horizontal = 16.dp)) {
            AudioPlayer(url = MockedData.testUrl, audioPlayer = disfluencyAudioPlayer)
        }
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
                )
                {
                    Text(
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                        text = word.getDisfluency(),
                        fontWeight = FontWeight.Bold
                    )
                }
                Text(
                    text = word.word + " ",
                    fontSize = 20.sp, modifier = Modifier.clickable { disfluencyAudioPlayer.seekTo(word.startTime) },
                    color = if (word.isTimeInBetween(disfluencyAudioPlayer.position())) MaterialTheme.colorScheme.primary else
                        Color.Black
                )

                // Se arregla en androidx.compose.foundation:foundation-layout:1.5.0 que agrega al FlowRow un VerticalArrangement
                Spacer(modifier = Modifier.height(16.dp))
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