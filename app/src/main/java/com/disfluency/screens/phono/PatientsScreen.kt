package com.disfluency.screens.phono

import android.util.Log
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
import com.disfluency.data.PatientRepository
import com.disfluency.model.Patient
import com.disfluency.model.Phono
import com.disfluency.navigation.Route
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun PatientsScreen(navController: NavHostController, user: Phono) {
    var text by rememberSaveable { mutableStateOf("") }

    val patients = remember { mutableStateListOf<Patient>() }

    LaunchedEffect(Unit) {
        val patientsResponse = withContext(Dispatchers.IO) { PatientRepository.getPatientsByTherapistId(user.id) }
        Log.i("HTTP", patientsResponse.toString())
        patients.addAll(patientsResponse)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            Modifier
                .semantics { isContainer = true }
                .zIndex(1f)
                .fillMaxWidth()
                .padding(bottom = 8.dp)) {
            //TODO: ver si se puede esconder el teclado cuando doy enter
            SearchBar(
                modifier = Modifier.align(Alignment.TopCenter),
                query = text,
                onQueryChange = { text = it },
                onSearch = { },
                active = false,
                onActiveChange = { },
                placeholder = { Text(stringResource(R.string.search_placeholder)) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            ) {}
        }
        PatientsList(patients.toList(), navController, text)
    }
    PatientCreation(navController)
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
    // Refactor a ListItem?
    val onClick = {
        navController.navigate(Route.Paciente.routeTo(patient.id))
    }

    Row(
        modifier = Modifier
            .padding(all = 8.dp)
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Image(
            painter = painterResource(patient.profilePic),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colorScheme.secondary, CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = patient.fullNameFormal(),
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(4.dp))

            Surface(shape = MaterialTheme.shapes.medium) {
                Text(
                    text = "${patient.age()} ${stringResource(R.string.years_old_unit)}",
                    modifier = Modifier.padding(all = 4.dp),
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
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
        ) {
            Icon(Icons.Filled.Add, "Creacion")
        }
    }
}