package com.disfluency.data

import com.disfluency.model.Phono
import com.disfluency.model.User

class UserNotFoundException: Exception()

object UserRepository {
    private val users = MockedData.users

    suspend fun findUser(username: String, password: String): User {
        return users.find { it.username == username && it.password == password } ?: throw UserNotFoundException()
    }
}