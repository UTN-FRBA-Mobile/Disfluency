package com.disfluency.clients

import com.disfluency.data.UserDTO
import com.disfluency.model.Exercise
import com.disfluency.model.ExerciseAssignment
import com.disfluency.model.ExercisePractice
import com.disfluency.model.Patient
import com.disfluency.model.dto.AssignmentDTO
import com.disfluency.model.dto.PracticeDTO
import com.disfluency.model.dto.RoleDTO
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

    @POST("/login")
    suspend fun login(@Body user: UserDTO): RoleDTO

    @POST("/therapists/{therapistId}/exercises/assignment")
    suspend fun assignExercisesToPatients(@Body assignment: AssignmentDTO, @Path("therapistId") therapistId: String)

    @GET("/therapists/{therapistId}/exercises")
    suspend fun getExercisesFrom(@Path("therapistId") therapistId: String): List<Exercise>

    @GET("exercisesAssignments/{exerciseId}")
    suspend fun getExercisesAssignmentById(@Path("exerciseId") exerciseId: String): ExerciseAssignment

    @GET("exercises/{exerciseId}")
    suspend fun getExerciseById(@Path("exerciseId") exerciseId: String): Exercise

    @GET("patient/{patientId}/exerciseAssignments")
    suspend fun getAssignmentsByPatientId(@Path("patientId") patientId: String): List<ExerciseAssignment>

    @POST("exercisesAssignments/{exerciseId}/practices")
    suspend fun createPracticeInAssignment(@Path("exerciseId") exerciseId: String, @Body recordingUrl: PracticeDTO)

    @GET("exercisesAssignments/{exerciseId}/practices/{practiceId}")
    suspend fun getPracticeByExerciseIdAndPracticeId(): ExercisePractice
}
