package com.disfluency.model

import com.disfluency.model.serialization.DayDeserializer
import com.disfluency.model.serialization.DaySerializer
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.time.Period
import java.time.*

data class Patient(
    @JsonProperty("name")
    val name: String,

    @JsonProperty("lastName")
    val lastName: String,

    @JsonProperty("dateOfBirth")
    @JsonDeserialize(using = LocalDateDeserializer::class)
    @JsonSerialize(using = LocalDateSerializer::class)
    val dateOfBirth: LocalDate,

    @JsonProperty("id")
    val id: String,

    @JsonProperty("email")
    val email: String,

    @JsonProperty("joinedSince")
    @JsonDeserialize(using = LocalDateDeserializer::class)
    @JsonSerialize(using = LocalDateSerializer::class)
    val joinedSince: LocalDate,

    @JsonProperty("profilePictureUrl")
    val profilePic: Int,

    @JsonProperty("weeklyTurn")
    @JsonDeserialize(using = DayDeserializer::class)
    @JsonSerialize(using = DaySerializer::class)
    val weeklyTurn: List<DayOfWeek>, //TODO: ver cual seria el tipo de dato para esto

    @JsonProperty("weeklyHour")
    @JsonDeserialize(using = LocalTimeDeserializer::class)
    @JsonSerialize(using = LocalTimeSerializer::class)
    val weeklyHour: LocalTime, //TODO: ver cual seria el tipo de dato para esto

    @JsonProperty("exercises")
    val exercises: MutableList<ExerciseAssignment> = ArrayList()
) : Role {

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

    fun getCompletedExercisesCount(): Int {
        return exercises
            .map { e -> e.practiceAttempts }
            .count { e -> e.isNotEmpty() }
    }

    fun getPendingExercisesCount(): Int {
        return exercises
            .map { e -> e.practiceAttempts }
            .count { e -> e.isEmpty() }
    }

    // TODO
    fun getCompletedQuestionnairesCount(): Int {
        return 3
    }

    fun getPendingQuestionnairesCount(): Int {
        return 0
    }

    fun nextTurnFromDateTime(dateTime: LocalDateTime): LocalDateTime {
        if(weeklyTurn.isEmpty()){
            throw NoTurnsAssignedException(this)
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

class NoTurnsAssignedException(patient: Patient): Exception("Patient ${patient.fullName()} hasn't weekly turns asigned")
