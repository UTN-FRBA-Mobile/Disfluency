package com.disfluency.components.user

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.disfluency.R
import com.disfluency.data.PatientRepository
import com.disfluency.model.Patient
import com.disfluency.ui.theme.MyApplicationTheme

@Composable
fun PatientInfoCard(patient: Patient){
    Card(
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(top = 20.dp, bottom = 20.dp, start = 16.dp, end = 16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.avatar_26),
                contentDescription = "User Thumbnail",
                modifier = Modifier.size(90.dp)
            )

            //TODO: que se ajuste el texto cuando no hay espacio
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = patient.fullName(),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row {
                    IconLabeled(
                        icon = Icons.Outlined.CalendarMonth,
                        label = patient.weeklyTurn,
                        content = "Turn"
                    )
                    
                    Spacer(modifier = Modifier.width(8.dp))
                    
                    IconLabeled(
                        icon = Icons.Outlined.AccessTime,
                        label = patient.weeklyHour,
                        content = "Time"
                    )
                }
            }
        }
    }
}

@Composable
fun IconLabeled(icon: ImageVector, label: String, content: String){
    Icon(
        imageVector = icon,
        contentDescription = content,
        modifier = Modifier.size(20.dp)
    )
    Text(
        text = label,
        style = MaterialTheme.typography.labelMedium,
        color = Color.Black,
        modifier = Modifier
            .padding(start = 2.dp)
            .height(20.dp)
            .wrapContentHeight()
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewInfoCard(){
    MyApplicationTheme() {
        PatientInfoCard(patient = PatientRepository.testPatient)
    }
}