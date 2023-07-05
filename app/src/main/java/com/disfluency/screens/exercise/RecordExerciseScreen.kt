package com.disfluency.screens.exercise

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.disfluency.audio.record.DisfluencyAudioRecorder
import com.disfluency.audio.record.MAX_SPIKES
import com.disfluency.components.audio.AudioMediaType
import com.disfluency.components.audio.AudioPlayer
import com.disfluency.components.audio.LiveWaveform
import com.disfluency.components.button.RecordAudioButton
import com.disfluency.data.ExerciseRepository
import com.disfluency.model.Exercise
import com.disfluency.navigation.Route
import java.io.File

const val LOCAL_RECORD_FILE = "disfluency_exercise_recording.mp3"


@Composable
fun RecordExercise(id: Int, onSend: (File) -> Unit, navController: NavController){
    val exercise = ExerciseRepository.getExerciseById(id)

    val audioRecorder = DisfluencyAudioRecorder(LocalContext.current)

    var recordingDone by remember { mutableStateOf(false) }
    val changeRecordingState = { recordingDone = !recordingDone }

    //TODO: if recording done y trata de salir de la pantalla, mostrar un dialog preguntando si esta seguro.

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
            ExerciseTitleWithInfo(
                exercise = exercise,
                onInfoButtonClick = {
                    navController.navigate(Route.Ejercicio.routeTo(exercise.id))
                }
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

            //TODO: animacion al cambio

            if (recordingDone)
                AudioPlayer(audio = LOCAL_RECORD_FILE, type = AudioMediaType.FILE)
            else
                LiveWaveform(amplitudes = audioRecorder.audioAmplitudes, maxSpikes = MAX_SPIKES, maxHeight = 160.dp)
        }

        RecordButton(audioRecorder, changeRecordingState, onSend)

    }
}

@Composable
private fun ExerciseTitleWithInfo(exercise: Exercise, onInfoButtonClick: () -> Unit){
    val modifier = Modifier.size(26.dp).padding(horizontal = 2.dp)

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = modifier)

        Text(text = exercise.title, style = MaterialTheme.typography.headlineSmall)

        IconButton(
            modifier = modifier.offset(y = 2.dp),
            onClick = onInfoButtonClick
        ) {
            Icon(imageVector = Icons.Filled.Info, contentDescription = "Info", tint = MaterialTheme.colorScheme.primary)
        }
    }
}

@Composable
fun RecordButton(audioRecorder: DisfluencyAudioRecorder, changeRecordingState: () -> Unit, onSend: (File) -> Unit){
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
                changeRecordingState()
                audioRecorder.stop()
            },
            onSend = {
                File(context.cacheDir, LOCAL_RECORD_FILE).let(onSend)
                //TODO: algun pequeño efecto o animacion y volver para atras
            },
            onCancel = {
                changeRecordingState()
                audioRecorder.audioAmplitudes.clear()
                audioFile?.apply { delete() }
                audioFile = null
            }
        )
    }
}
