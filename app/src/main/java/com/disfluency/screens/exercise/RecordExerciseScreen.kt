package com.disfluency.screens.exercise

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.disfluency.audio.record.DisfluencyAudioRecorder
import com.disfluency.components.audio.LiveWaveform
import com.disfluency.audio.record.MAX_SPIKES
import com.disfluency.components.audio.AudioMediaType
import com.disfluency.components.audio.AudioPlayer
import com.disfluency.components.button.RecordAudioButton
import com.disfluency.data.ExerciseRepository
import com.disfluency.model.Exercise
import com.disfluency.navigation.BottomNavigation
import com.disfluency.navigation.Route
import com.disfluency.ui.theme.MyApplicationTheme
import java.io.File

const val LOCAL_RECORD_FILE = "disfluency_exercise_recording.mp3"

@Composable
@Preview(showBackground = true)
fun RecordExercisePreview(){
    val navController = rememberNavController()

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
                BottomNavigation(navController = navController)
            },
            content = { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    RecordExercise(id = 4, onSend = {
                        println("Send")
                    }, navController = navController)

                }
            }
        )
    }
}

@Composable
fun RecordExercise(id: Int, onSend: (File) -> Unit, navController: NavController){
    val exercise = ExerciseRepository.getExerciseById(id)

    val audioRecorder = DisfluencyAudioRecorder(LocalContext.current)

    var recordingDone by remember { mutableStateOf(false) }
    val changeRecordingState = { recordingDone = !recordingDone }

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
                audioRecorder.stop()
                audioFile?.let(onSend)
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
