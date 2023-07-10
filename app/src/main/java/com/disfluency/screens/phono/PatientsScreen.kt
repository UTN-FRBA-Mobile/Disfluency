package com.disfluency.screens.phono

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.isContainer
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.disfluency.R
import com.disfluency.components.user.IconLabeled
import com.disfluency.components.user.weeklyTurnFormat
import com.disfluency.data.PatientRepository
import com.disfluency.loading.SkeletonLoader
import com.disfluency.loading.skeleton.patient.PatientListSkeleton
import com.disfluency.model.Patient
import com.disfluency.model.Phono
import com.disfluency.navigation.Route
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.format.DateTimeFormatter


@Composable
fun PatientsScreen(navController: NavHostController, user: Phono) {
    var text by rememberSaveable { mutableStateOf("") }

    val patients: MutableState<List<Patient>?> = remember { mutableStateOf(null) }

    LaunchedEffect(Unit) {
        val patientsResponse = withContext(Dispatchers.IO) { PatientRepository.getPatientsByTherapistId(user.id) }
        Log.i("HTTP", patientsResponse.toString())
        patients.value = patientsResponse
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.onPrimaryContainer
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                Modifier
                    .semantics { isContainer = true }
                    .zIndex(1f)
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 16.dp)) {
                //TODO: ver si se puede esconder el teclado cuando doy enter
                SearchBar(
                    modifier = Modifier.align(Alignment.TopCenter),
                    query = text,
                    onQueryChange = { text = it },
                    onSearch = { },
                    active = false,
                    onActiveChange = { },
                    placeholder = { Text("Buscar") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                ) {}
            }

            SkeletonLoader(
                state = patients,
                content = {
                    patients.value?.let {
                        PatientsList(it, navController, text)
                    }
                },
                skeleton = {
                    PatientListSkeleton()
                }
            )

        }
        PatientCreation(navController)
    }

}

@Composable
fun PatientsList(patients: List<Patient>, navController: NavHostController, filter: String) {
    LazyColumn(contentPadding = PaddingValues(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(patients.filter {
                patient -> patient.fullName().contains(filter, true) }) {patient ->
            PatientCard(patient, navController)
        }
    }
}

@Composable
fun PatientCard(patient: Patient, navController: NavHostController) {
    val onClick = {
        navController.navigate(Route.Paciente.routeTo(patient.id))
    }

    Card(
        modifier = Modifier.clickable { onClick() },
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
    ) {
        ListItem(
            modifier = Modifier.height(56.dp),
            headlineContent = {
                Text(
                    text = patient.fullNameFormal(),
                    style = MaterialTheme.typography.titleMedium
                )
            },
            supportingContent = {
                Text(
                    text = weeklyTurnFormat(patient.weeklyTurn),
                    style = MaterialTheme.typography.labelMedium
                )
            },
            leadingContent = {
                Image(
                    painter = painterResource(patient.profilePic),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .border(1.5.dp, MaterialTheme.colorScheme.secondary, CircleShape)
                )
            },
            trailingContent = {
                IconLabeled(
                    icon = Icons.Outlined.AccessTime,
                    label = patient.weeklyHour.format(
                        DateTimeFormatter.ofPattern(stringResource(
                        R.string.time_format))),
                    content = "Time"
                )
            }
        )
    }


}

@Composable
fun PatientCreation(navController: NavHostController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        FloatingActionButton(
            onClick = {
                navController.navigate(Route.NuevoPaciente.route)
            },
            modifier = Modifier.padding(16.dp),
            containerColor = MaterialTheme.colorScheme.secondary
        ) {
            Icon(Icons.Filled.Add, "Creacion")
        }
    }
}