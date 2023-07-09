package com.disfluency.screens.utils

import com.disfluency.data.UserNotFoundException
import com.disfluency.data.UserRepository
import com.disfluency.model.User

class LoginService {
    private var user: User? = null

    suspend fun login(username: String, password: String, fcmToken: String){
        val user = UserRepository.login(username, password, fcmToken)
        this.user = User("", "", user)
    }

    fun getUser(): User {
        return user ?: throw UserNotFoundException()
    }

    suspend fun logout(){
        this.user = null
    }
}