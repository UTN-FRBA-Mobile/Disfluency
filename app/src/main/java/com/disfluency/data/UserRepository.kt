package com.disfluency.data

import com.disfluency.clients.DisfluencyAPIServiceGenerator
import com.disfluency.model.Role
import retrofit2.HttpException

class UserNotFoundException: Exception()

object UserRepository {

    private val apiService = DisfluencyAPIServiceGenerator.buildService()

    suspend fun login(username: String, password: String, fcmToken: String): Role {
        try {
            val roleDTO = apiService.login(UserDTO(username, password, fcmToken))
            return roleDTO.toRole()
        } catch (e: HttpException) {
            throw UserNotFoundException()
        }
    }
}

data class UserDTO(val account: String, val password: String, val fcmToken: String)