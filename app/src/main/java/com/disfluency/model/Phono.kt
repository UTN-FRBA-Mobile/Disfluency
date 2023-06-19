package com.disfluency.model

class Phono(val patients: MutableList<Patient> = ArrayList()): Role {
    fun addPatient(newPatient: Patient){
        this.patients.add(newPatient)
    }
}