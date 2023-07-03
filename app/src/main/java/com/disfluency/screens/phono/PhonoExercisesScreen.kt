package com.disfluency.screens.phono

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.disfluency.data.PatientRepository

@Composable
fun PhonoExercisesScreen(id: Int) {
    val patient = PatientRepository.getPatientById(id)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = "Ejercicios del Paciente: ${patient.name}",
            fontWeight = FontWeight.Bold,
            color = Color.Gray,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 20.sp
        )
    }
}