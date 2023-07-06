package com.disfluency.data

import com.disfluency.model.Exercise
import com.disfluency.model.ExerciseAssignment
import com.disfluency.model.ExercisePractice
import com.disfluency.model.Patient

object ExerciseRepository {
    val exercises = MockedData.exercises
    val longListForTest = exercises

    fun getExerciseById(id: Int): Exercise{
        return exercises.first { it.id == id }
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

    fun getPracticeById(id: String): ExercisePractice {
        return MockedData.practices.first { it.id == id }
    }

    fun getExerciseDetailOfPractice(id: String): Exercise {
        return MockedData.assignments.first {
            it.practiceAttempts.map(ExercisePractice::id).contains(id)
        }.exercise
    }
}