package com.disfluency.data

import com.disfluency.model.Exercise
import com.disfluency.model.ExerciseAssignment
import com.disfluency.model.ExercisePractice
import com.disfluency.model.Patient
import java.io.File
import java.time.LocalDate

object ExerciseRepository {
    val exercises = MockedData.exercises
    val longListForTest = exercises

    fun getExerciseById(id: Int): Exercise{
        return exercises.first { it.id == id }
    }

    fun saveExercisePractice(assignmentId: String, audio: File){
        val practice = ExercisePractice(
            id = assignmentId,
            recordingUrl = "", //TODO: subir audio
            date = LocalDate.now()
        )
    }

    fun getCompletedExercisesCountByPatient(patient: Patient): Int {
        return 15; //TODO: implement
    }

    fun getPendingExercisesCountByPatient(patient: Patient): Int {
        return 2; //TODO: implement
    }

    fun getAssignmentById(id: String): ExerciseAssignment{
        return MockedData.assignments.first { it.id == id }
    }
}