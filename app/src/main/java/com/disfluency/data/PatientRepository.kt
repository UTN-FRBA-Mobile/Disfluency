package com.disfluency.data

import com.disfluency.R
import com.disfluency.model.Patient
import java.time.LocalDate

object PatientRepository {

    fun getPatientById(id: Int): Patient{
        return patients.first { patient -> patient.id == id }
    }

    fun addPatient(patient: Patient) {
        patients.add(patient)
    }

    val testPatient = Patient("Agustin", "Cragno", LocalDate.of(1998, 7, 30), 40123864, "acragno@frba.utn.edu.ar", LocalDate.of(2023, 1, 9), R.drawable.avatar_26, "Lunes y Miercoles", "18:00")

    val patients = ArrayList<Patient>(listOf(
        Patient("Agustin", "Cragno", LocalDate.of(1998, 7, 30), 40123864, "acragno@frba.utn.edu.ar", LocalDate.of(2023, 1, 9), R.drawable.avatar_26, "Lunes y Miercoles", "18:00"),
        Patient("Jose", "Bruzzoni", LocalDate.of(1991, 2, 3), 43181238, "jbruzzoni@frba.utn.edu.ar", LocalDate.of(2018, 12, 9), R.drawable.avatar_12, "Martes y Jueves", "19:00"),
        Patient("Alexander", "Martinez", LocalDate.of(1995, 5, 4), 37186477, "amartinez@gmail.com", LocalDate.of(2023, 5, 15), R.drawable.avatar_17, "Miercoles", "19:00")
    ))


    val longListForTest = List(20){
        patients.random()
    }
}

