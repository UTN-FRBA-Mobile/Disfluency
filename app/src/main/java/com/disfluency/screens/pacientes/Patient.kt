package com.disfluency.screens.pacientes

import java.time.LocalDate
import java.time.temporal.ChronoUnit

data class Patient(val name: String, val lastname: String, val dni: String, val bornDate: LocalDate){
    val getAgeAtDate: (LocalDate)->Number = {
        bornDate.until(it, ChronoUnit.YEARS)
    }
}