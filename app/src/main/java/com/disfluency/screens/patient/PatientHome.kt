package com.disfluency.screens.patient

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material3.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.disfluency.R
import com.disfluency.components.grid.TwoColumnGridItemSpan
import com.disfluency.components.user.IconLabeled
import com.disfluency.components.user.PatientInfoCard
import com.disfluency.components.user.weeklyTurnFormat
import com.disfluency.data.*
import com.disfluency.model.Patient
import com.disfluency.model.Phono
import com.disfluency.navigation.Route
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Preview
@Composable
fun Preview(){
    PatientHome(user = PatientRepository.patients.first(), navController = rememberNavController())
}

@Composable
fun PatientHome(user: Patient, navController: NavController){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 4.dp)
    ) {
     //   PatientInfoCard(user)
        WelcomeCard(user)
        Column(verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
        ) {
            ActivitiesOverview(patient = user, navController)
            PhonoCard(phono = UserRepository.getPhonoForPatient(user))
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun WelcomeCard(patient: Patient) {
    val todaysDate = LocalDateTime.now()
    val daysTillNextTurn = Duration.between(todaysDate, patient.nextTurnFromDateTime(todaysDate)).toDays()
    val suffix = if(daysTillNextTurn == 0L) "hoy" else "dentro de $daysTillNextTurn día${if(daysTillNextTurn>1)"s" else ""}"

    OutlinedCard(
        Modifier
            .fillMaxWidth()
            .padding(all = 16.dp)
    ) {
        Row(
            modifier = Modifier.padding(all = 12.dp).height(120.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = Modifier
                    .clip(shape = CircleShape)
                    .size(size = 95.dp),
                painter = painterResource(id = R.drawable.disfluency_logo),
                contentDescription = "",
                contentScale = ContentScale.Crop,

            )
            Spacer(Modifier.width(width = 8.dp))
            Column(Modifier.wrapContentWidth(unbounded = false)) {
                Text(
                    text = "¡Hola ${patient.name}!",
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Tu próximo turno será $suffix"/*"Tenés turno el $day $dateAsString a las $hourAsString  hs"*/,
                    style = MaterialTheme.typography.labelMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                FlowRow(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth().padding(end=16.dp)) {
                    IconLabeled(
                        icon = Icons.Outlined.CalendarMonth,
                        label = weeklyTurnFormat(patient.weeklyTurn),
                        content = "Turn"
                    )

                    IconLabeled(
                        icon = Icons.Outlined.AccessTime,
                        label = patient.weeklyHour.format(DateTimeFormatter.ofPattern(stringResource(R.string.time_format))),
                        content = "Time"
                    )
                }
            }
        }
    }
}

@Composable
fun PhonoCard(phono: Phono){
    Card(
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(end = 16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "${phono.name} ${phono.lastName}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    maxLines = 2
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text("Ing. en Fonoaudiología", style=MaterialTheme.typography.titleSmall, fontStyle = FontStyle.Italic)
            }

            Image(
                painter = painterResource(id = phono.profilePictureUrl),
                contentDescription = "Phono Thumbnail",
                modifier = Modifier.size(50.dp)
            )
        }
    }
}

@Composable
fun ActivitiesOverview(patient: Patient, navController: NavController) {

    data class ActivityOverviewItem(val title: String, val number: Int, val onClick: ()->Unit)
    val context = LocalContext.current
    val comingSoonMessage = stringResource(R.string.coming_soon)

    val activities = listOf(
        ActivityOverviewItem(
            stringResource(R.string.single_patient_button_pending_exercises),
            ExerciseRepository.getPendingExercisesCountByPatient(patient)
        ) { navController.navigate(Route.Ejercicios.route) },
        ActivityOverviewItem(
            stringResource(R.string.single_patient_button_pending_questionnaires),
            QuestionnaireRepository.getPendingQuestionnairesCountByPatient(patient),
        ) { Toast.makeText(context, comingSoonMessage, Toast.LENGTH_SHORT).show() },
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .padding(16.dp)
            .height(200.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        activities.forEachIndexed { index, activity ->
            item(span = { TwoColumnGridItemSpan(index, activities.size) }) {
                Box(Modifier.clickable {activity.onClick()}) {
                    ActivityOverviewCard(title = activity.title, number = activity.number)
                }
            }
        }
    }
}

@Composable
fun ActivityOverviewCard(title: String, number: Int){
    Card(
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
        modifier = Modifier
            .height(200.dp)
            .fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                maxLines = 2,
                lineHeight = 20.sp,
                fontSize = 20.sp, //TODO: se podra hacer que se ajuste al espacio disponible?
                color = Color.Black,
                textAlign = TextAlign.Center
            )
            Surface(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape),
                color = MaterialTheme.colorScheme.primary
            ) {
                Box(contentAlignment = Alignment.Center){
                    Text(
                        text = number.toString(),
                        style = TextStyle(color = Color.White, fontSize = 18.sp)
                    )
                }
            }
        }
    }
}