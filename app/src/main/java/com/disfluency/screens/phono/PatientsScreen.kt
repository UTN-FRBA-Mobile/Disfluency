package com.disfluency.screens.phono

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.isContainer
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.disfluency.components.user.IconLabeled
import com.disfluency.data.MockedData
import com.disfluency.R
import com.disfluency.components.user.weeklyTurnFormat
import com.disfluency.model.Patient
import com.disfluency.model.Phono
import com.disfluency.navigation.Route
import com.disfluency.ui.theme.MyApplicationTheme
import java.time.format.DateTimeFormatter

@Preview(showBackground = true)
@Composable
fun PatientsPreview(){
    MyApplicationTheme {
        PatientsScreen(navController = rememberNavController(), user = MockedData.therapists[0])
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PatientsScreen(navController: NavHostController, user: Phono) {
    var text by rememberSaveable { mutableStateOf("") }

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
                    .padding(vertical = 8.dp)) {
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
            PatientsList(user.patients, navController, text)
        }
        PatientCreation(navController)
    }

}

@Composable
fun PatientsList(pacientes: List<Patient>, navController: NavHostController, filter: String) {
    LazyColumn(contentPadding = PaddingValues(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(pacientes.filter {
                paciente -> paciente.fullName().contains(filter, true) }) {paciente ->
            PatientCard(paciente, navController)
        }
    }
}

@Composable
fun PatientCard(paciente: Patient, navController: NavHostController) {
    val onClick = {
        navController.navigate(Route.Paciente.routeTo(paciente.id))
    }

    Card(
        modifier = Modifier.clickable { onClick() },
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
    ) {
        ListItem(
            modifier = Modifier.height(56.dp),
            headlineContent = {
                Text(
                    text = paciente.fullNameFormal(),
                    style = MaterialTheme.typography.titleMedium
                )
            },
            supportingContent = {
                Text(
                    text = weeklyTurnFormat(paciente.weeklyTurn),
                    style = MaterialTheme.typography.labelMedium
                )
            },
            leadingContent = {
                Image(
                    painter = painterResource(paciente.profilePic),
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
                    label = paciente.weeklyHour.format(
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