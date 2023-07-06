package com.disfluency.screens.exercise

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.isContainer
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.disfluency.data.ExerciseRepository
import com.disfluency.model.Exercise
import com.disfluency.navigation.Route

@Composable
fun ExercisesScreen(navController: NavHostController) {
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
        ExerciseList(ExerciseRepository.longListForTest, navController, text)
    }
  //  ExerciseCreation(navController) //TODO
}

@Composable
fun ExerciseCreation(navController: NavHostController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        FloatingActionButton(
            onClick = {
                navController.navigate(Route.NuevoEjercicio.route)
            },
            modifier = Modifier.padding(16.dp),
        ) {
            Icon(Icons.Filled.Add, "Creacion")
        }
    }
}

@Composable
fun ExerciseList(exercises: List<Exercise>, navController: NavHostController, filter: String) {
    LazyColumn(contentPadding = PaddingValues(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(exercises.filter {
                ex -> ex.fullName().contains(filter, true) }) {ex ->
            ExerciseCard(ex, navController)
        }
    }
}


@Composable
fun ExerciseNumber(exercise: Exercise) {
    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .clip(CircleShape)
            .size(40.dp)
            .border(1.5.dp, MaterialTheme.colorScheme.secondary, CircleShape),
    ) {
        Box(contentAlignment = Alignment.Center){
            Text(
                text = exercise.number(),
                style = TextStyle(color = Color.White, fontSize = 18.sp)
            )
        }
    }
}

@Composable
fun Play(exercise: Exercise) {
    Button(
        onClick = { exercise.getAudioSample() },
        modifier = Modifier.size(40.dp),
        contentPadding = PaddingValues(1.dp)
    ) {
        Box {
            Icon(
                imageVector = Icons.Outlined.PlayArrow,
                contentDescription = exercise.title
            )
        }
    }
}

@Composable
fun ExerciseCard(exercise: Exercise, navController: NavHostController) {
    // Refactor a ListItem?
    val onClick = {
        navController.navigate(Route.Ejercicio.routeTo(exercise.id))
    }

    Row(
        modifier = Modifier
            .padding(all = 8.dp)
            .fillMaxWidth()
            .clickable { onClick() },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row (modifier = Modifier.weight(8f)) {
            ExerciseNumber(exercise = exercise)
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = exercise.title,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Surface(
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(
                        text = exercise.getFullInstructions(),
                        modifier = Modifier.padding(all = 4.dp),
                        maxLines = 1,
                        style = MaterialTheme.typography.labelMedium,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
//        Play(exercise)
    }
}
