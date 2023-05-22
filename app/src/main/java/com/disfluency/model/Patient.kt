package com.disfluency.model

import java.time.LocalDate
import java.time.Period

data class Patient(val name: String, val lastName: String, val dateOfBirth: LocalDate, val id: Int, val account: String, val joinedSince: LocalDate, val profilePic: Int){
    fun initials(): String {
        return (name.first().toString() + lastName.first().toString()).uppercase()
    }

    fun fullName(): String{
        return "$name $lastName"
    }

    fun age(): Int{
        return Period.between(
            dateOfBirth,
            LocalDate.now()
        ).years
    }
}
