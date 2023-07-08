package com.disfluency.data

import com.disfluency.clients.DisfluencyAPIServiceGenerator
import com.disfluency.model.Exercise
import com.disfluency.model.ExerciseAssignment
import com.disfluency.model.ExercisePractice
import com.disfluency.model.Patient
import java.io.File
import java.time.LocalDate

object ExerciseRepository {
    private val apiService = DisfluencyAPIServiceGenerator.buildService()
    val exercises = MockedData.exercises

    fun getExerciseById(id: String): Exercise {
        return exercises.first { it.id == id }
    }

    suspend fun getExerciseById2(id: String): Exercise {
        return apiService.getExerciseById(id)
    }

    suspend fun getExercisesByTherapistId(therapistId: String): List<Exercise> {
        return apiService.getExercisesFrom(therapistId)
    }

    fun saveExercisePractice(assignmentId: String, audio: File) {
        MockedPracticeSaver().save(assignmentId, audio)
        println("Saved audio recording: $audio")
    }

    fun getCompletedExercisesCountByPatient(patient: Patient): Int {
        return 15; //TODO: implement
    }

    fun getPendingExercisesCountByPatient(patient: Patient): Int {
        return 2; //TODO: implement
    }

    suspend fun getAssignmentById2(id: String): ExerciseAssignment {
        return apiService.getExercisesAssignmentById(id)
    }

    fun getAssignmentById(id: String): ExerciseAssignment {
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

interface ExercisePracticeSaver{
    fun save(assignmentId: String, audio: File)
}

class MockedPracticeSaver: ExercisePracticeSaver{
    override fun save(assignmentId: String, audio: File) {
        val practice = ExercisePractice(
            id = assignmentId,
            recordingUrl = MockedData.testUrl,
            date = LocalDate.now()
        )

        ExerciseRepository.getAssignmentById(assignmentId).practiceAttempts.add(practice)
    }
}