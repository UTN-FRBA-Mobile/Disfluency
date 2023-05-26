package com.disfluency.model

import java.time.LocalDate
import java.time.Period

data class Patient(
    val name: String,
    val lastName: String,
    val dateOfBirth: LocalDate,
    val id: Int,
    val account: String,
    val joinedSince: LocalDate,
    val profilePic: Int,
    val weeklyTurn: String, //TODO: ver cual seria el tipo de dato para esto
    val weeklyHour: String //TODO: ver cual seria el tipo de dato para esto
    ){

    fun initials(): String {
        return (name.first().toString() + lastName.first().toString()).uppercase()
    }

    fun fullName(): String{
        return "$name $lastName"
    }

    fun fullNameFormal(): String{
        return "$lastName, $name"
    }

    fun age(): Int{
        return Period.between(
            dateOfBirth,
            LocalDate.now()
        ).years
    }
}