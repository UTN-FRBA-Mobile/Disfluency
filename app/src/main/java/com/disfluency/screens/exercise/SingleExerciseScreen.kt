package com.disfluency.screens.exercise

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.disfluency.components.audio.AudioMediaType
import com.disfluency.components.audio.AudioPlayer
import com.disfluency.data.ExerciseRepository
import com.disfluency.model.Exercise
import com.disfluency.navigation.bottomNavigation.BottomNavigation
import com.disfluency.ui.theme.MyApplicationTheme
/*
@Preview(showBackground = true)
@Composable
fun ExercisePreview(){
    MyApplicationTheme {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text(text = "Ejercicio") },
                    navigationIcon = { Icon(Icons.Filled.Menu , contentDescription = "") },
                    actions = { Icon(Icons.Filled.AccountCircle, contentDescription = "") }
                )
            },
            bottomBar = {
                BottomNavigation(navController = rememberNavController())
            },
            content = { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    SingleExerciseScreen(id = 4)
                }
            }
        )
    }
}*/

@Composable
fun SingleExerciseScreen(id: Int) {
    val exercise = ExerciseRepository.getExerciseById(id)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = exercise.title,
                style = MaterialTheme.typography.headlineSmall
            )

            Text(
                text = exercise.instruction,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(8.dp),
            )

           exercise.phrase?.let{
                Text(
                    text = "Repita la siguiente frase:",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 4.dp, top = 24.dp)
                )

                Text(
                    text = "\"${exercise.phrase}\"",
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    textAlign = TextAlign.Center,
                    fontStyle = FontStyle.Italic
                )
            }
        }
        
        ExampleRecording(exercise)
    }
}

@Composable
fun ExampleRecording(exercise: Exercise){
    Column {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            text = "Ejemplo generado por el profesional",
            style = MaterialTheme.typography.labelMedium
        )

        AudioPlayer(audio = exercise.sampleAudioURL, type = AudioMediaType.URL)
    }

}