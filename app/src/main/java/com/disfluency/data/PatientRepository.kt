package com.disfluency.data

import com.disfluency.clients.DisfluencyAPIServiceGenerator
import com.disfluency.model.Patient

object PatientRepository {

    private val apiService = DisfluencyAPIServiceGenerator.buildService()
    fun getPatientById2(id: String): Patient{
        return patients.first { it.id == id }
    }

    suspend fun getPatientById(id: String): Patient{
        return apiService.getPatientById(id)
    }

    fun addPatient(patient: Patient) {
        patients.add(patient)
    }

    suspend fun getPatientsByTherapistId(): List<Patient>{
        return apiService.getPatientsByTherapistId("64a89fe4c11df54d8a9477e3")
    }

    val patients = MockedData.patients
}
