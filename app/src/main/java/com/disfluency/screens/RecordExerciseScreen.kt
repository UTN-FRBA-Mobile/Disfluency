package com.disfluency.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.disfluency.audio.record.DisfluencyAudioRecorder
import com.disfluency.audio.record.LiveWaveform
import com.disfluency.audio.record.MAX_SPIKES
import com.disfluency.components.audio.AudioMediaType
import com.disfluency.components.audio.AudioPlayer
import com.disfluency.components.button.RecordAudioButton
import com.disfluency.data.ExerciseRepository
import com.disfluency.navigation.BottomNavigation
import com.disfluency.ui.theme.MyApplicationTheme
import java.io.File

const val LOCAL_RECORD_FILE = "disfluency_exercise_recording.mp3"

@Composable
@Preview(showBackground = true)
fun RecordExercisePreview(){

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

                    RecordExercise(id = 4)

                }
            }
        )
    }
}

@Composable
fun RecordExercise(id: Int){
    val exercise = ExerciseRepository.getExerciseById(id)

    val audioRecorder = DisfluencyAudioRecorder(LocalContext.current)

    var recordingDone by remember { mutableStateOf(false) }
    val changeRecordingState = {
        recordingDone = !recordingDone
    }

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

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp),
            contentAlignment = Alignment.Center
        ){

            //TODO: fade in out on change

            if (recordingDone)
                AudioPlayer(audio = LOCAL_RECORD_FILE, type = AudioMediaType.FILE)
            else
                LiveWaveform(amplitudes = audioRecorder.audioAmplitudes, maxSpikes = MAX_SPIKES, maxHeight = 160.dp)
        }

        RecordButton(audioRecorder, changeRecordingState)

    }
}

@Composable
fun RecordButton(audioRecorder: DisfluencyAudioRecorder, changeRecordingState: () -> Unit){
    val context = LocalContext.current

    var audioFile: File? = null

    Row(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RecordAudioButton(
            onClick = {
                File(context.cacheDir, LOCAL_RECORD_FILE).also {
                    audioRecorder.start(it)
                    audioFile = it
                }
            },
            onRelease = {
                audioRecorder.stop()
                changeRecordingState()
            },
            onSend = {
                audioRecorder.stop()
                //TODO: subir el audio y crear el objeto de ejericio resuelto

                //TODO: borrar el audio local cuando me vaya de esta pantalla
            },
            onCancel = {
                changeRecordingState()
                audioRecorder.stop()
                audioRecorder.audioAmplitudes.clear() //es temporal esto
            }
        )
    }
}
