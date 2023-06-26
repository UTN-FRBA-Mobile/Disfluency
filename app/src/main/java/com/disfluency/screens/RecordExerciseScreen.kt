package com.disfluency.screens

import android.content.res.Resources
import android.content.res.loader.ResourcesProvider
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.disfluency.audio.playback.DisfluencyAudioPlayer
import com.disfluency.audio.record.DisfluencyAudioRecorder
import com.disfluency.audio.record.MAX_SPIKES
import com.disfluency.audio.record.LiveWaveform
import com.disfluency.components.button.RecordSwipeButton
import com.disfluency.data.ExerciseRepository
import com.disfluency.navigation.BottomNavigation
import com.disfluency.ui.theme.MyApplicationTheme
import java.io.File
import java.io.FileInputStream

@Composable
@Preview(showBackground = true)
fun RecordExercisePreview(){

    MyApplicationTheme() {
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

                    RecordSwipeButton(
                        onClick = { println("Press") },
                        onRelease = { println("Released") },
                        onSend = { println("Send") },
                        onCancel = { println("Cancel") }
                    )

                }
            }
        )
    }
}

@Composable
fun RecordExercise(id: Long){
    val exercise = ExerciseRepository.getExerciseById(id)

    val audioRecorder = DisfluencyAudioRecorder(LocalContext.current)

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

            //TODO: se puede dejar mas espacio sacando el assignment y agregando algun boton para poder verlo
            // se me ocurre un boton de info al lado del titulo que al tocarlo te trae un pop up con la
            // descripcion del ej y el audio de ejemplo para reproducir
//            Text(
//                text = exercise.assignment,
//                style = MaterialTheme.typography.bodyMedium,
//                textAlign = TextAlign.Center,
//                modifier = Modifier.padding(8.dp),
//            )

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

        LiveWaveform(amplitudes = audioRecorder.audioAmplitudes, maxSpikes = MAX_SPIKES, maxHeight = 160.dp)

        RecordButton(audioRecorder)

    }
}

//TODO: cuando dejo de grabar,
// se tiene que ir el boton, y tiene que irse la onda y mostrarme la onda completa del audio (con reproductor)
// y me aparece abajo un boton de enviar o confirmar

@Composable
fun RecordButton(audioRecorder: DisfluencyAudioRecorder){
    val context = LocalContext.current

    var audioFile: File? = null

    val audioPlayer = DisfluencyAudioPlayer(context)

    Row(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RecordSwipeButton(
            onClick = {
                println("Start!")
                File(context.cacheDir, "audio.mp3").also {
                    audioRecorder.start(it)
                    audioFile = it
                }
            },
            onRelease = {
                println("Release!")
                audioRecorder.stop()
            },
            onSend = {
                println("Send!")
                audioRecorder.stop()
            },
            onCancel = {
                println("Cancel!")
                audioRecorder.stop()
                audioRecorder.audioAmplitudes.clear() //es temporal esto
            }
        )
    }
}
