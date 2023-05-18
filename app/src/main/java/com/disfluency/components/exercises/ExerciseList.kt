package com.disfluency.components.exercises

import android.content.res.Resources.Theme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.disfluency.data.ExerciseRepository
import com.disfluency.data.PatientRepository
import com.disfluency.model.ExerciseTask
import com.disfluency.model.Patient
import com.disfluency.ui.theme.MyApplicationTheme
import java.time.format.DateTimeFormatter

@Composable
fun ExerciseList(patient: Patient){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Text(text = "Ejercicios asignados", color = Color.Gray, fontWeight = FontWeight.SemiBold)

        Spacer(modifier = Modifier.height(6.dp))

        LazyColumn {
            items(ExerciseRepository.getExercisesByPatient(patient)){ exercise ->
                ExerciseItem(exerciseTask = exercise)

            }
        }
    }
}

@Composable
fun ExerciseItem(exerciseTask: ExerciseTask){
    val taskHasEntries = ExerciseRepository.doesTaskHaveEntries(exerciseTask)

    ListItem(
        headlineContent = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = exerciseTask.exercise.title)

                if (taskHasEntries){
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "completado",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(8, 126, 8, 255), //TODO: reemplazar por color en los themes
                        modifier = Modifier.align(Alignment.Bottom)
                    )
                }
            }
        },
        supportingContent = {
            Text(
                text = exerciseTask.exercise.phraseAsQuote(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.Gray
            )
        },
        trailingContent = {
            Text(
                text = exerciseTask.date.format(DateTimeFormatter.ofPattern("MMM dd")),
                style = MaterialTheme.typography.bodySmall
            )
        }
    )

    Divider()
}

@Preview(showBackground = true)
@Composable
fun PreviewExerciseList(){
    ExerciseList(patient = PatientRepository.testPatient)
}