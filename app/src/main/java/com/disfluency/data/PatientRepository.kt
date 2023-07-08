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

    suspend fun addPatientToTherapist(patient: Patient, therapistId: String): Patient {
        return apiService.addPatientToTherapist(patient, therapistId)
    }

    suspend fun getPatientsByTherapistId(id: String): List<Patient>{
        return apiService.getPatientsByTherapistId(id)
    }

    val patients = MockedData.patients
}
