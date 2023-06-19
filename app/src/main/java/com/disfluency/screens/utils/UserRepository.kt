package com.disfluency.screens.utils

import com.disfluency.data.PatientRepository
import com.disfluency.model.Phono
import kotlinx.coroutines.delay

class UserNotFoundException: Exception()

object UserRepository {
    private val users = PatientRepository.patients.map { User(it.name, "123", it) } + listOf(
        User("beto", "123", Phono())
    )

    suspend fun findUser(username: String, password: String): User{
        delay(300)
        return users.find { it.username == username && it.password == password } ?: throw UserNotFoundException()
    }
}