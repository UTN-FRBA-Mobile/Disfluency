package com.disfluency.data

import com.disfluency.model.Patient
import com.disfluency.model.Phono
import com.disfluency.model.User

class UserNotFoundException: Exception()
class PhonoNotFoundException: Exception()

object UserRepository {
    private val users = MockedData.users

    suspend fun findUser(username: String, password: String): User {
        return users.find { it.username == username && it.password == password } ?: throw UserNotFoundException()
    }

    fun getPhonoForPatient(patient: Patient): Phono {
        return (users.map { it.role }.find { it is Phono && it.patients.any { pat -> pat.id == patient.id}} ?: throw PhonoNotFoundException()) as Phono
    }
}