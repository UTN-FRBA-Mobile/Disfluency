package com.disfluency.data

import com.disfluency.model.Phono
import com.disfluency.model.User

class UserNotFoundException: Exception()

object UserRepository {
    private val users = usersStub()

    suspend fun findUser(username: String, password: String): User {
        return users.find { it.username == username && it.password == password } ?: throw UserNotFoundException()
    }

    private fun usersStub(): List<User>{
        val patients = PatientRepository.patients


        return patients.map { User(it.name, "123", it) } + listOf(
            User("beto", "123", Phono(ArrayList(patients))),
            User("Matias", "123", Phono(mutableListOf(patients.get(0))))
        )
    }
}