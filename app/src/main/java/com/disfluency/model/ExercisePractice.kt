package com.disfluency.model

import java.time.LocalDate

data class ExercisePractice(
    val id: Int,
    val date: LocalDate,
    val audioUrl: String
) {
}