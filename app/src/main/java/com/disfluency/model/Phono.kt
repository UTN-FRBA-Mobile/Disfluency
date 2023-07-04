package com.disfluency.model

//TODO: cambiar nombre a Therapist y todas las referencias de 'phono'
data class Phono(
    val id: String,
    val name: String,
    val lastName: String,
    val profilePictureUrl: Int,
    val patients: MutableList<Patient> = ArrayList(),
    val exercises: MutableList<Exercise> = ArrayList()
): Role {
    fun addPatient(newPatient: Patient){
        this.patients.add(newPatient)
    }
}