package com.disfluency.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.disfluency.data.PatientRepository

@Composable
fun PacientesScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        SinglePatientScreen(PatientRepository.testPatient) //TODO: agregar un boton que me lleve aca por el momento
    }
}