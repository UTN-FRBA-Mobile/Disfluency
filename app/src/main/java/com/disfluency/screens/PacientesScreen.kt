package com.disfluency.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.disfluency.data.PacienteRepository
import com.disfluency.model.Paciente
import com.disfluency.navigation.Route


@Composable
fun PacientesScreen(navController: NavHostController) {
    Column {
        SearchBar() //TODO: ver de usar la SearchBar de material
        PacientesList(PacienteRepository.longListForTest, navController)
    }
    PacienteCreation(navController)
}

@Composable
fun PacientesList(pacientes: List<Paciente>, navController: NavHostController) {
    LazyColumn {
        items(pacientes) {paciente ->
            PacienteCard(paciente, navController)
        }
    }
}


@Composable
fun SearchBar() {
    var searchTerm by remember { mutableStateOf("") }
    TextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = searchTerm,
        onValueChange = { searchTerm = it },
        placeholder = { Text(text = "Buscar") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Buscador"
            )
        },
        trailingIcon = {
            if (searchTerm.isNotEmpty()) {
                IconButton(
                    onClick = { searchTerm = "" },
                ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Borrar busqueda"
                    )
                }
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        singleLine = true
    )
}

@Composable
fun PacienteCard(paciente: Paciente, navController: NavHostController) {
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
fun PacienteCreation(navController: NavHostController) {
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