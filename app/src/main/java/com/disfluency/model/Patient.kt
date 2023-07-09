package com.disfluency.model

import com.disfluency.model.utils.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.time.Period

data class Patient(
    val name: String,
    val lastName: String,
    val dateOfBirth: LocalDate,
    val id: Int,
    val email: String,
    val joinedSince: LocalDate,
    val profilePictureUrl: Int,
    val weeklyTurn: List<DayOfWeek>, //TODO: ver cual seria el tipo de dato para esto
    val weeklyHour: LocalTime, //TODO: ver cual seria el tipo de dato para esto
    val exercises: MutableList<ExerciseAssignment> = ArrayList()
    ): Role {

    fun initials(): String {
        return (name.first().toString() + lastName.first().toString()).uppercase()
    }

    fun fullName(): String {
        return "$name $lastName"
    }

    fun fullNameFormal(): String {
        return "$lastName, $name"
    }

    fun age(): Int {
        return Period.between(
            dateOfBirth,
            LocalDate.now()
        ).years
    }
}