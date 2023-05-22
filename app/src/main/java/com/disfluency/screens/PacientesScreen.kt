package com.disfluency.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.disfluency.R
import java.time.LocalDate
import java.time.Period
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType

data class Paciente(val nombre: String,
                    val apellido: String,
                    val fechaNac: LocalDate,
                    val drawable: Int)

val pacientesList = listOf<Paciente>(
    Paciente("José", "Bruzzoni", LocalDate.parse("1997-11-08"), R.drawable.yo),
    Paciente("Agustín", "Cragno", LocalDate.parse("1999-11-08"), R.drawable.agus),
    Paciente("Matias", "Anzorandia", LocalDate.parse("1998-11-08"), R.drawable.matias),
    Paciente("Julian", "Simaro", LocalDate.parse("1995-11-08"), R.drawable.juli),
    Paciente("José", "Bruzzoni", LocalDate.parse("1997-11-08"), R.drawable.yo),
    Paciente("Agustín", "Cragno", LocalDate.parse("1999-11-08"), R.drawable.agus),
    Paciente("Matias", "Anzorandia", LocalDate.parse("1998-11-08"), R.drawable.matias),
    Paciente("Julian", "Simaro", LocalDate.parse("1995-11-08"), R.drawable.juli),
    Paciente("José", "Bruzzoni", LocalDate.parse("1997-11-08"), R.drawable.yo),
    Paciente("Agustín", "Cragno", LocalDate.parse("1999-11-08"), R.drawable.agus),
    Paciente("Matias", "Anzorandia", LocalDate.parse("1998-11-08"), R.drawable.matias),
    Paciente("Julian", "Simaro", LocalDate.parse("1995-11-08"), R.drawable.juli),
    Paciente("José", "Bruzzoni", LocalDate.parse("1997-11-08"), R.drawable.yo),
    Paciente("Agustín", "Cragno", LocalDate.parse("1999-11-08"), R.drawable.agus),
    Paciente("Matias", "Anzorandia", LocalDate.parse("1998-11-08"), R.drawable.matias),
    Paciente("Julian", "Simaro", LocalDate.parse("1995-11-08"), R.drawable.juli),
    Paciente("José", "Bruzzoni", LocalDate.parse("1997-11-08"), R.drawable.yo),
    Paciente("Agustín", "Cragno", LocalDate.parse("1999-11-08"), R.drawable.agus),
    Paciente("Matias", "Anzorandia", LocalDate.parse("1998-11-08"), R.drawable.matias),
    Paciente("Julian", "Simaro", LocalDate.parse("1995-11-08"), R.drawable.juli),
    Paciente("José", "Bruzzoni", LocalDate.parse("1997-11-08"), R.drawable.yo),
    Paciente("Agustín", "Cragno", LocalDate.parse("1999-11-08"), R.drawable.agus),
    Paciente("Matias", "Anzorandia", LocalDate.parse("1998-11-08"), R.drawable.matias),
    Paciente("Julian", "Simaro", LocalDate.parse("1995-11-08"), R.drawable.juli),
    Paciente("José", "Bruzzoni", LocalDate.parse("1997-11-08"), R.drawable.yo),
    Paciente("Agustín", "Cragno", LocalDate.parse("1999-11-08"), R.drawable.agus),
    Paciente("Matias", "Anzorandia", LocalDate.parse("1998-11-08"), R.drawable.matias),
    Paciente("Julian", "Simaro", LocalDate.parse("1995-11-08"), R.drawable.juli)
)

@Composable
fun PacientesScreen() {
    Column {
        SearchBar()
        PacientesList(pacientesList)
    }
    PacienteCreation()
}

@Composable
fun PacientesList(pacientes: List<Paciente>) {
    LazyColumn {
        items(pacientes) {paciente ->
            PacienteCard(paciente)
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
fun PacienteCard(paciente: Paciente) {
    Row(modifier = Modifier
        .padding(all = 8.dp)
        .fillMaxWidth()) {
        Image(
            painter = painterResource(paciente.drawable),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colorScheme.secondary, CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = paciente.apellido+", "+paciente.nombre,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(4.dp))

            Surface(shape = MaterialTheme.shapes.medium) {
                Text(
                    text = Period.between(
                            paciente.fechaNac,
                            LocalDate.now()
                        ).years.toString() + " años",
                    modifier = Modifier.padding(all = 4.dp),
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun PacienteCreation() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        FloatingActionButton(
            onClick = { /* do something */ },
            modifier = Modifier.padding(16.dp),
        ) {
            Icon(Icons.Filled.Add, "Creacion")
        }
    }
}