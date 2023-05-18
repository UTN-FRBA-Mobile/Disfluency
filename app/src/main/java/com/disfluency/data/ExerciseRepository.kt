package com.disfluency.data

import com.disfluency.model.Exercise
import com.disfluency.model.ExerciseTask
import com.disfluency.model.ExerciseTaskEntry
import com.disfluency.model.Patient
import java.time.LocalDate

object ExerciseRepository {
    val fonacionContinuada = Exercise(
        title = "Fonación continuada",
        description = "Repetir la siguiente frase de una manera continua manteniendo el mismo ritmo de manera constante durante toda la frase",
        phrase = "El TP contará con una nota individual, basada en lo presentado en las entregas parciales y final y teniendo en cuenta la contribución de cada integrante en el repositorio"
    )

    val toqueLigero = Exercise(
        title = "Toque Ligero",
        description = "Repetir las siguientes palabras produciendo las consonantes con movimientos y toques suaves entre las zonas de contacto. Empezar todas las palabras que comienzan con consonante con una suave producción; toco y me voy; evito la presión en el resto de las consonantes de la palabra.",
        phrase = "lapiz - mesa - sopa - silla"
    )

    val exercises = listOf(fonacionContinuada, toqueLigero)

    val taskFC = ExerciseTask(
        exercise = fonacionContinuada,
        patient = PatientRepository.testPatient,
        date = LocalDate.of(2023, 2, 18)
    )

    val taskTL = ExerciseTask(
        exercise = toqueLigero,
        patient = PatientRepository.testPatient,
        date = LocalDate.of(2023, 4, 2)
    )

    val tasks = listOf(taskFC, taskTL)

    val taskEntryFC = ExerciseTaskEntry(
        exerciseTask = taskFC,
        date = LocalDate.of(2023, 2, 27),
        audio = "El TP contará con una nota individual, basada en lo presentado"
    )

    val taskEntries = listOf(taskEntryFC)

    fun getExercisesByPatient(patient: Patient): List<ExerciseTask> {
        return tasks.filter { task -> task.patient == patient }
    }

    fun getFinishedExercisesByPatient(patient: Patient): List<ExerciseTaskEntry> {
        return taskEntries.filter { entry -> entry.exerciseTask.patient == patient }
    }

    fun doesTaskHaveEntries(exerciseTask: ExerciseTask): Boolean {
        return taskEntries.any { entry -> entry.exerciseTask == exerciseTask }
    }
}