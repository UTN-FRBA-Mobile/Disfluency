package com.disfluency.screens.patient

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.disfluency.R
import com.disfluency.data.PatientRepository
import com.disfluency.model.Patient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun PatientQuestionnairesScreen(id: String) {
    val patient = remember { mutableStateOf<Patient?>(null) }

    LaunchedEffect(Unit) {
        val aPatient = withContext(Dispatchers.IO) { PatientRepository.getPatientById2(id) }
        Log.i("LOG", aPatient.toString())
        patient.value = aPatient
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        patient.value?.let {
            Text(
                text = "Cuestionarios del Paciente: ${it.name}",
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )
        }

    }
}