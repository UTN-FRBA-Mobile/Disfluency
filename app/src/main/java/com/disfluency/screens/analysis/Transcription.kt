package com.disfluency.screens.analysis

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.disfluency.components.user.IconLabeledDetails
import com.disfluency.components.user.PatientInfoCard
import com.disfluency.data.PatientRepository
import com.disfluency.model.analysis.*
import com.disfluency.ui.theme.MyApplicationTheme

@Composable
fun Transcription(id: Int) {
    val patient = PatientRepository.getPatientById(id)
    val disfluencyType = DisfluencyType.RF
    val disfluencyType2 = DisfluencyType.M
    val disfluency = SingleDisfluency(disfluencyType)
    val compositeDisfluency = CompositeDisfluency(listOf(disfluencyType, disfluencyType2))
    val analysis = Analysis(listOf(
        AnalysedWord("Me", disfluency),
        AnalysedWord("parece", disfluency),
        AnalysedWord("que"),
        AnalysedWord("esto", compositeDisfluency),
        AnalysedWord("no"),
        AnalysedWord("funciona", disfluency),
        AnalysedWord("muy", compositeDisfluency),
        AnalysedWord("bien")))

    Column(modifier = Modifier.fillMaxWidth()) {
        PatientInfoCard(patient = patient,
            firstLabel = IconLabeledDetails(Icons.Outlined.Person, patient.age().toString(), "Analysis"),
            secondLabel = IconLabeledDetails(Icons.Outlined.CalendarMonth, "22/06/23", "Date")
        )
        LazyVerticalGrid(
            contentPadding = PaddingValues(start = 8.dp, top = 16.dp, end = 8.dp, bottom = 16.dp),
            columns = GridCells.Adaptive(minSize = 80.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            items(analysis.analysedWords) {
                word -> Column(modifier = Modifier.fillMaxWidth()) {
                Surface(shape = MaterialTheme.shapes.medium) {
                    Text(word.getDisfluency())
                }
                Text(word.word,
                    Modifier.fillMaxWidth())
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TranscriptionPreview(){
    MyApplicationTheme {
        Transcription(id = 40123864)
    }
}