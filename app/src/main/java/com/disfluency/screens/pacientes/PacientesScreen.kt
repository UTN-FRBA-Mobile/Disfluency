package com.disfluency.screens.pacientes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import java.time.LocalDate

val patients = ArrayList<Patient>()

@Composable
fun PacientesScreen() {
    var displayedNewPatient by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        if(displayedNewPatient){

            Column(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Button(onClick = { displayedNewPatient = false }) {
                    Text(text = "Cancelar")
                }

                FormNewPatient {
                    patients.add(it)
                    displayedNewPatient = false
                }
            }
        } else {
            Text(
                text = "Mis Pacientes",
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )

            Button(onClick = { displayedNewPatient=true }){
                Text(text = "Nuevo Paciente")
            }

            val date = LocalDate.now()
            patients.forEachIndexed { index, paciente ->
                Text(text = "#${index+1} ${paciente.name} ${paciente.lastname}\n${paciente.dni}\n${paciente.getAgeAtDate(date)} años")
            }
        }
    }
}