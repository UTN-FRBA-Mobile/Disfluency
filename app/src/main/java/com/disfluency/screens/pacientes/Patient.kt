package com.disfluency.screens.pacientes

import java.time.LocalDate
import java.time.temporal.ChronoUnit

data class Patient(val name: String, val lastname: String, val dni: String, val birthDate: LocalDate){
    val getAgeAtDate: (LocalDate)->Number = {
        birthDate.until(it, ChronoUnit.YEARS)
    }
}