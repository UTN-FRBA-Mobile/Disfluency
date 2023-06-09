package com.disfluency.screens.exercise

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.isContainer
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.disfluency.R
import com.disfluency.components.list.items.ExerciseListItem
import com.disfluency.data.ExerciseRepository
import com.disfluency.loading.SkeletonLoader
import com.disfluency.loading.skeleton.exercise.ExerciseListSkeleton
import com.disfluency.model.Exercise
import com.disfluency.model.Phono
import com.disfluency.navigation.Route
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun ExercisesScreen(navController: NavHostController, phono: Phono) {
    var text by rememberSaveable { mutableStateOf("") }
    val exercises: MutableState<List<Exercise>?> = remember { mutableStateOf(null) }

    LaunchedEffect(Unit) {
        val exercisesResponse = withContext(Dispatchers.IO) { ExerciseRepository.getExercisesByTherapistId(phono.id) }
        Log.i("HTTP", exercisesResponse.toString())
        exercises.value = exercisesResponse
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
                    placeholder = { Text(stringResource(R.string.exercises_search_placeholder)) },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                ) {}
            }
            SkeletonLoader(
                state = exercises,
                content = {
                    exercises.value?.let {
                        ExerciseList(it, navController, text)
                    }
                },
                skeleton = {
                    ExerciseListSkeleton()
                }
            )

        }
    }
}

@Composable
fun ExerciseList(exercises: List<Exercise>, navController: NavHostController, filter: String) {
    LazyColumn(contentPadding = PaddingValues(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(exercises.filter { ex -> ex.fullName().contains(filter, true) }) {ex ->
            ExerciseListItem(ex, onClick = {
                navController.navigate(Route.Ejercicio.routeTo(ex.id))
            })
        }
    }
}