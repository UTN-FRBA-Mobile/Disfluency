package com.disfluency.data

import com.disfluency.R
import com.disfluency.clients.DisfluencyAPIServiceGenerator
import com.disfluency.model.Role
import retrofit2.HttpException
import com.disfluency.model.Patient
import com.disfluency.model.Phono
import com.disfluency.model.User
class UserNotFoundException: Exception()
class PhonoNotFoundException: Exception()

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

    fun getPhonoForPatient(patient: Patient): Phono {
        //TODO: resolver sin mockeo
        return Phono("", "Lionel", "Scaloni", R.drawable.avatar_1)
//    return (.map { it.role }.find { it is Phono && it.patients.any { pat -> pat.id == patient.id}} ?: throw PhonoNotFoundException()) as Phono
    }
}

data class UserDTO(val account: String, val password: String, val fcmToken: String)



