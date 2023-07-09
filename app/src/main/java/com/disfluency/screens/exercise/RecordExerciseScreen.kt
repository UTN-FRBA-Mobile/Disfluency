package com.disfluency.screens.exercise

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.disfluency.audio.record.DisfluencyAudioRecorder
import com.disfluency.components.audio.AudioMediaType
import com.disfluency.components.audio.AudioPlayer
import com.disfluency.components.audio.LiveWaveform
import com.disfluency.components.button.RecordAudioButton
import com.disfluency.components.dialog.ExitDialog
import com.disfluency.data.ExerciseRepository
import com.disfluency.model.Exercise
import com.disfluency.navigation.Route
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

const val LOCAL_RECORD_FILE = "disfluency_exercise_recording.mp3"

@Composable
fun RecordExercise(id: String, onSend: (File) -> Unit, navController: NavController){
    val exercise = remember { mutableStateOf<Exercise?>(null) }

    LaunchedEffect(Unit) {
        val anExercise = withContext(Dispatchers.IO) { ExerciseRepository.getExerciseById(id) }
        Log.i("HTTP", anExercise.toString())
        exercise.value = anExercise
    }

    val audioRecorder = DisfluencyAudioRecorder(LocalContext.current)

    var recordingDone by remember { mutableStateOf(false) }
    val changeRecordingState = { recordingDone = !recordingDone }

    var openDialog by remember { mutableStateOf(false) }
    var exitActionBack by remember { mutableStateOf(true) }

    BackHandler(enabled = recordingDone) {
        exitActionBack = true
        openDialog = true
    }

    exercise.value?.let {
        if (openDialog){
            ExitDialog(
                title = "¿Esta seguro que desea salir?",
                content = "Se perdera la grabacion realizada. Antes de salir deberia confirmar la resolucion del ejercicio o descartarla.",
                cancel = { openDialog = false },
                exit = {
                    openDialog = false
                    recordingDone = false
                    if (exitActionBack) navController.popBackStack()
                    else navController.navigate(Route.Ejercicio.routeTo(it.id))
                }
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            ExercisePhraseDetail(exercise = it, onInfoButtonClick = {
                if (recordingDone){
                    exitActionBack = false
                    openDialog = true
                } else {
                    //TODO: ver como queda con un dialog en vez de salir
                    navController.navigate(Route.Ejercicio.routeTo(it.id))
                }
            })

            RecordingVisualizer(audioRecorder = audioRecorder, hasRecorded = recordingDone)

            RecordButton(audioRecorder = audioRecorder, changeRecordingState = changeRecordingState, onSend = onSend)
        }
    }
}

@Composable
fun ExercisePhraseDetail(exercise: Exercise, onInfoButtonClick: () -> Unit){
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ExerciseTitleWithInfo(
            exercise = exercise,
            onInfoButtonClick = onInfoButtonClick
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
}

@Composable
private fun ExerciseTitleWithInfo(exercise: Exercise, onInfoButtonClick: () -> Unit){
    val modifier = Modifier
        .size(26.dp)
        .padding(horizontal = 2.dp)

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
fun RecordingVisualizer(audioRecorder: DisfluencyAudioRecorder, hasRecorded: Boolean){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp),
        contentAlignment = Alignment.Center
    ){
        AnimatedVisibility(
            visible = hasRecorded,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            AudioPlayer(url = LOCAL_RECORD_FILE, type = AudioMediaType.FILE)
        }

        AnimatedVisibility(
            visible = !hasRecorded,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            LiveWaveform(amplitudes = audioRecorder.audioAmplitudes, maxHeight = 160.dp)
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