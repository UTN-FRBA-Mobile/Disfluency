package com.disfluency.data

import com.disfluency.R
import com.disfluency.model.Patient
import java.time.LocalDate

object PatientRepository {
    fun getPatientById(id: Int): Patient{
        return patients.first { it.id == id }
    }

    fun addPatient(patient: Patient) {
        patients.add(patient)
    }

    val patients = MockedData.patients
}

