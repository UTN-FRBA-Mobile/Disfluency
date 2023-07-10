package com.disfluency.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSetter
import com.fasterxml.jackson.annotation.Nulls

//TODO: cambiar nombre a Therapist y todas las referencias de 'phono'
data class Phono(
    @JsonProperty("id")
    val id: String,

    @JsonProperty("name")
    val name: String,

    @JsonProperty("lastName")
    val lastName: String,

    @JsonProperty("profilePictureUrl")
    val profilePictureUrl: Int,

    @JsonProperty("patients")
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    val patients: MutableList<Patient> = ArrayList(),

    @JsonProperty("exercises")
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    val exercises: MutableList<Exercise> = ArrayList()
): Role {
    fun addPatient(newPatient: Patient){
        this.patients.add(newPatient)
    }
}