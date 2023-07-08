package com.disfluency.clients

import com.disfluency.model.Patient
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface DisfluencyAPIService {

    @GET("/therapists/{therapistId}/patients")
    suspend fun getPatientsByTherapistId(@Path("therapistId") therapistId: String): List<Patient>

    @GET("patient/{patientId}")
    suspend fun getPatientById(@Path("patientId") patientId: String): Patient

    @POST("/therapists/{therapistId}/patients")
    suspend fun addPatientToTherapist(@Body patient: Patient, @Path("therapistId") therapistId: String): Patient
}
