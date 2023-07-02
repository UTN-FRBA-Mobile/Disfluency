package com.disfluency.model

import java.time.LocalDate

data class ExerciseAssignment(
    val id: Int,
    val exerciseId: Int,
    val patientId: Int,
    val data: LocalDate
) {
}