package com.disfluency.screens.login

import androidx.navigation.NavController
import com.disfluency.model.Patient
import com.disfluency.navigation.Route

class LoginService {
    private var user: User? = null

    suspend fun login(username: String, password: String){
        val user = UserRepository.findUser(username, password)
        this.user = user
    }

    fun getUser(): User{
        return user?:throw UserNotFoundException()
    }
}