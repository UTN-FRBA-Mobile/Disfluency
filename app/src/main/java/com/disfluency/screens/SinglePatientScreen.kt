package com.disfluency.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Assignment
import androidx.compose.material.icons.outlined.Mic
import androidx.compose.material.icons.outlined.RecordVoiceOver
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.disfluency.components.grid.TwoColumnGridItemSpan
import com.disfluency.components.user.PatientInfoCard
import com.disfluency.data.ExerciseRepository
import com.disfluency.data.PatientRepository
import com.disfluency.data.QuestionnaireRepository
import com.disfluency.data.TherapySessionRepository
import com.disfluency.model.Patient
import com.disfluency.ui.theme.MyApplicationTheme

@Composable
fun SinglePatientScreen(id: Int){
    val patient = PatientRepository.getPatientById(id)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        PatientInfoCard(patient = patient)
        ButtonPanel(patient = patient)
        ActivitiesOverview(patient = patient)
    }
}


@Composable
fun ButtonPanel(patient: Patient){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        ActivityButton(patient = patient, title = "Ejercicios", icon = Icons.Outlined.RecordVoiceOver)
        ActivityButton(patient = patient, title = "Cuestionarios", icon = Icons.Outlined.Assignment)
        ActivityButton(patient = patient, title = "Sesiones", icon = Icons.Outlined.Mic)
    }
}

@Composable
fun ActivityButton(patient: Patient, title: String, icon: ImageVector){
    Column(
        horizontalAlignment = CenterHorizontally
    ) {
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier.size(42.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary
            ),
            contentPadding = PaddingValues(1.dp)
        ) {
            Box {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }

        Text(
            text = title,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            fontWeight = FontWeight.SemiBold
        )
    }

}

@Composable
fun ActivitiesOverview(patient: Patient){

    data class ActivityOverviewItem(val title: String, val number: Int)

    val activities = listOf(
        ActivityOverviewItem("Ejercicios Resueltos", ExerciseRepository.getCompletedExercisesCountByPatient(patient)),
        ActivityOverviewItem("Ejercicios Pendientes", ExerciseRepository.getPendingExercisesCountByPatient(patient)),
        ActivityOverviewItem("Cuestionarios Resueltos", QuestionnaireRepository.getCompletedQuestionnairesCountByPatient(patient)),
        ActivityOverviewItem("Cuestionarios Pendientes", QuestionnaireRepository.getPendingQuestionnairesCountByPatient(patient)),
        ActivityOverviewItem("Sesiones Grabadas", TherapySessionRepository.getSessionCountByPatient(patient))
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .padding(16.dp)
            .height(248.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){
        activities.forEachIndexed { index, activity ->
            item(span = { TwoColumnGridItemSpan(index, activities.size) }) {
                ActivityOverviewCard(title = activity.title, number = activity.number)
            }
        }
    }
}

@Composable
fun ActivityOverviewCard(title: String, number: Int){
    Card(
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
        modifier = Modifier
            .height(72.dp)
            .fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape),
                color = MaterialTheme.colorScheme.primary
            ) {
                Box(contentAlignment = Center){
                    Text(
                        text = number.toString(),
                        style = TextStyle(color = Color.White, fontSize = 18.sp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .fillMaxHeight()
                    .wrapContentHeight(),
                maxLines = 2,
                lineHeight = 20.sp,
                fontSize = 13.sp, //TODO: se podra hacer que se ajuste al espacio disponible?
                color = Color.Black
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SinglePatientScreenPreview(){
    MyApplicationTheme() {
        SinglePatientScreen(id = 40123864)
    }
}

