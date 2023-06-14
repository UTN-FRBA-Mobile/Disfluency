package com.disfluency.data

import com.disfluency.model.Exercise
import com.disfluency.model.Patient

object ExerciseRepository {

    val fonacionContinuada = Exercise(
        1,
        1,
        "Fonacion Continuada",
        "Mantener la fonacion a lo largo de la palabra y entre palabra y palabra. Mantener la vibracion de las cuerdas vocales a lo largo de la palabra y entre palabras sin frenar, y sostener durante toda una frase",
        "La portabilidad es la capacidad del producto o componente de ser transferido de forma efectiva y eficiente de un entorno hardware, software, operacional o de utilización a otro."
    )

    private val exercises = listOf(
        fonacionContinuada
    )

    fun getExerciseById(id: Long): Exercise {
        return exercises.first { exercise -> exercise.id == id }
    }

    fun getCompletedExercisesCountByPatient(patient: Patient): Int {
        return 15; //TODO: implement
    }

    fun getPendingExercisesCountByPatient(patient: Patient): Int {
        return 2; //TODO: implement
    }


}