package com.disfluency.screens.utils

import kotlinx.coroutines.delay

class LoginService {
    private var user: User? = null

    suspend fun login(username: String, password: String){
        val user = UserRepository.findUser(username, password)
        this.user = user
    }

    fun getUser(): User {
        return user ?: throw UserNotFoundException()
    }

    suspend fun logout(){
        delay(300)
        this.user = null
    }
}