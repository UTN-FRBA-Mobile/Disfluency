package com.disfluency.components.user

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.disfluency.data.PatientRepository
import com.disfluency.model.Patient
import java.time.format.DateTimeFormatter

@Composable
fun PatientInfoCard(patient: Patient){
    Surface(
        shape = MaterialTheme.shapes.medium,
        shadowElevation = 1.dp,
        color = MaterialTheme.colorScheme.surface,
        modifier = Modifier.fillMaxWidth().padding(15.dp)
    ){
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            UserThumbnailNoImage(patient = patient)

            Column(
                Modifier.padding(start = 15.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
            ){
                Text(text = patient.fullName(), style = MaterialTheme.typography.titleLarge)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .alpha(0.7f),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "${patient.age()} años", style = MaterialTheme.typography.bodyMedium)
                    Text(text = "paciente desde ${DateTimeFormatter.ofPattern("dd/MM/yyyy").format(patient.joinedSince)}", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}

@Composable
fun UserThumbnailNoImage(patient: Patient){
    Surface(
        modifier = Modifier
            .size(50.dp)
            .clip(CircleShape)
            .border(0.7.dp, Color.Black, CircleShape),
        color = Color.Red //TODO: Los colores de fondo y texto se pueden generar distintos para cada usuario
    ) {
        Box(contentAlignment = Alignment.Center){
            Text(
                text = patient.initials(),
                style = TextStyle(color = Color.White, fontSize = 20.sp)
            )
        }

    }

}

@Preview(showBackground = true)
@Composable
fun PreviewInfoCard(){
    PatientInfoCard(patient = PatientRepository.testPatient)
}