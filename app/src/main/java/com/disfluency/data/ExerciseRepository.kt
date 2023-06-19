package com.disfluency.data

import com.disfluency.R
import com.disfluency.model.Exercise
import com.disfluency.model.Patient
import java.time.LocalDate

object ExerciseRepository {
    val exercises = listOf(
        Exercise(id = 1, therapistId = 1, title = "Presentarse", instruction = "Di tu nombre y apellido como muestra el ejemplo", sampleAudioURL = "aasad.com", number = 1),
        Exercise(id = 2, therapistId = 1, title = "Repetir", instruction = "Repite la siguiente frase", phrase = "Tres tigres comen trigo Tres tigres comen trigoTres tigres comen trigo Tres tigres comen trigo Tres tigres comen trigo Tres tigres comen trigo Tres tigres comen trigo v Tres tigres comen trigo Tres tigres comen trigo", sampleAudioURL = "aasad.com", number = 2),
        Exercise(id = 3, therapistId = 1, title = "Repetir 2", instruction = "Repita la siguiente frase", phrase = "Pablito clavó un clavito", sampleAudioURL = "aasad.com", number = 3)
    )

    val longListForTest = List(20){
        exercises.random()
    }

    fun getExerciseById(id: Int): Exercise{
        return exercises.first { exercise -> exercise.id == id }
    }

    fun getCompletedExercisesCountByPatient(patient: Patient): Int {
        return 15; //TODO: implement
    }

    fun getPendingExercisesCountByPatient(patient: Patient): Int {
        return 2; //TODO: implement
    }
}