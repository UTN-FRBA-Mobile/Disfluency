package com.disfluency.model

import java.time.*

data class Patient(
    val name: String,
    val lastName: String,
    val dateOfBirth: LocalDate,
    val id: Int,
    val email: String,
    val joinedSince: LocalDate,
    val profilePic: Int,
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

    fun nextTurnFromDateTime(dateTime: LocalDateTime): LocalDateTime {
        if(weeklyTurn.isEmpty()){
            throw NoTurnsAsignedException(this)
        }
        var nextTurnDate: LocalDate? = null
        var nextDate = dateTime.toLocalDate()

        while(nextTurnDate==null){
            if(weeklyTurn.contains(nextDate.dayOfWeek) && !(dateTime.toLocalDate().isEqual(nextDate) && weeklyHour.isBefore(dateTime.toLocalTime()))) {
                nextTurnDate = nextDate
            }
            nextDate = nextDate.plusDays(1)
        }

        return LocalDateTime.of(nextTurnDate, weeklyHour)
    }

    fun daysTillNextTurnFromDate(dateTime: LocalDateTime): Long{
        return Duration.between(dateTime.toLocalDate().atStartOfDay(), nextTurnFromDateTime(dateTime).toLocalDate().atStartOfDay()).toDays()
    }
}

class NoTurnsAsignedException(patient: Patient): Exception("Patient ${patient.fullName()} hasn't weekly turns asigned")
