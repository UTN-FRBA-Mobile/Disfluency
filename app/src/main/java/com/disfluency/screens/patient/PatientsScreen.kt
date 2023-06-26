package com.disfluency.screens.patient

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.isContainer
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.disfluency.data.PatientRepository
import com.disfluency.model.Patient
import com.disfluency.navigation.Route

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PacientesScreen(navController: NavHostController) {
    var text by rememberSaveable { mutableStateOf("") }

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
                placeholder = { Text("Buscar") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            ) {}
        }
        PatientsList(PatientRepository.longListForTest, navController, text)
    }
    PatientCreation(navController)
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
    // Refactor a ListItem?
    val onClick = {
        navController.navigate(Route.Paciente.routeTo(paciente.id))
    }

    Row(
        modifier = Modifier
            .padding(all = 8.dp)
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Image(
            painter = painterResource(paciente.profilePic),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colorScheme.secondary, CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = paciente.fullNameFormal(),
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(4.dp))

            Surface(shape = MaterialTheme.shapes.medium) {
                Text(
                    text = "${paciente.age()} años",
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