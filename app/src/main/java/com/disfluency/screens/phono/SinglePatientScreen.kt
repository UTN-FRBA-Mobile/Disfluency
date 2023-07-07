package com.disfluency.screens.phono

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.disfluency.R
import com.disfluency.components.grid.TwoColumnGridItemSpan
import com.disfluency.components.user.PatientInfoCard
import com.disfluency.data.ExerciseRepository
import com.disfluency.data.PatientRepository
import com.disfluency.data.QuestionnaireRepository
import com.disfluency.data.TherapySessionRepository
import com.disfluency.model.Patient
import com.disfluency.navigation.Route
import com.disfluency.ui.theme.MyApplicationTheme

@Composable
fun SinglePatientScreen(id: Int, navController: NavController){
    val patient = PatientRepository.getPatientById(id)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        PatientInfoCard(patient = patient)
        ButtonPanel(patient = patient, navController = navController)
        ActivitiesOverview(patient = patient)
    }
}


@Composable
fun ButtonPanel(patient: Patient, navController: NavController){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        ActivityButton(title = stringResource(R.string.single_patient_button_exercises), icon = Icons.Outlined.RecordVoiceOver, onClick = { navController.navigate(Route.PatientExercises.routeTo(patient.id)) })
        ActivityButton(title = stringResource(R.string.single_patient_button_questionnaires), icon = Icons.Outlined.Assignment, onClick = { navController.navigate(Route.PatientQuestionnaires.routeTo(patient.id)) })
        ActivityButton(title = stringResource(R.string.single_patient_button_sessions), icon = Icons.Outlined.Mic, onClick = { navController.navigate(Route.PatientSessions.routeTo(patient.id)) })
    }
}

@Composable
fun ActivityButton(title: String, icon: ImageVector, onClick: () -> Unit){
    Column(
        horizontalAlignment = CenterHorizontally
    ) {
        Button(
            onClick = onClick,
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
        ActivityOverviewItem(stringResource(R.string.single_patient_button_solved_exercises), ExerciseRepository.getCompletedExercisesCountByPatient(patient)),
        ActivityOverviewItem(stringResource(R.string.single_patient_button_pending_exercises), ExerciseRepository.getPendingExercisesCountByPatient(patient)),
        ActivityOverviewItem(stringResource(R.string.single_patient_button_solved_questionnaires), QuestionnaireRepository.getCompletedQuestionnairesCountByPatient(patient)),
        ActivityOverviewItem(stringResource(R.string.single_patient_button_pending_questionnaires), QuestionnaireRepository.getPendingQuestionnairesCountByPatient(patient)),
        ActivityOverviewItem(stringResource(R.string.single_patient_button_registered_sessions), TherapySessionRepository.getSessionCountByPatient(patient))
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
        SinglePatientScreen(id = 40123864, rememberNavController())
    }
}

