package com.disfluency.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.disfluency.components.exercises.ExerciseList
import com.disfluency.components.user.PatientInfoCard
import com.disfluency.data.PatientRepository
import com.disfluency.model.Patient

@Composable
fun SinglePatientScreen(patient: Patient){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        PatientInfoCard(patient = patient)
        ExerciseList(patient = patient)
    }
}

@Preview(showBackground = true)
@Composable
fun SinglePatientScreenPreview(){
    SinglePatientScreen(patient = PatientRepository.testPatient)
}

