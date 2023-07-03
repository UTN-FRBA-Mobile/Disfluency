package com.disfluency.screens.utils

import com.disfluency.data.UserNotFoundException
import com.disfluency.data.UserRepository
import com.disfluency.model.User
import kotlinx.coroutines.delay

class LoginService {
    private var user: User? = null

    suspend fun login(username: String, password: String){
        delay(50)
        val user = UserRepository.findUser(username, password)
        this.user = user
    }

    fun getUser(): User {
        return user ?: throw UserNotFoundException()
    }

    suspend fun logout(){
        delay(50)
        this.user = null
    }
}