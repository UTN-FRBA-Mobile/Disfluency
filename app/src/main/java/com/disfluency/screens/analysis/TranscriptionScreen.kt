package com.disfluency.screens.analysis

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
            AnalysedWord("Me", disfluency),
            AnalysedWord("parece", disfluency),
            AnalysedWord("que"),
            AnalysedWord("esto", compositeDisfluency),
            AnalysedWord("no"),
            AnalysedWord("funciona", disfluency),
            AnalysedWord("muy", compositeDisfluency),
            AnalysedWord("bien"),
            AnalysedWord("ah", disfluency),
            AnalysedWord("para!", disfluency),
            AnalysedWord("re"),
            AnalysedWord("funciona", compositeDisfluency),
            AnalysedWord("ahora"),
            AnalysedWord("nice", disfluency),
            AnalysedWord("murcielago", compositeDisfluency),
            AnalysedWord("chau")
        )
    )

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
        Transcription(analysis)
    }
}

@Composable
@OptIn(ExperimentalLayoutApi::class)
private fun Transcription(analysis: Analysis) {
    FlowRow(Modifier.padding(start = 8.dp, top = 16.dp, end = 8.dp, bottom = 16.dp)) {
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
                Text(text = word.word + " ", fontSize = 20.sp)
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