package com.disfluency.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty

interface Role

data class User(
    val username: String,

    @JsonIgnore
    val password: String,

    @JsonProperty("userRole")
    val role: Role
    ){
    fun profilePic(): Int{
        return when (role) {
            is Patient -> role.profilePic
            is Phono -> role.profilePictureUrl
            else -> { throw NoPictureDefinedForRole(role)}
        }
    }
}

class NoPictureDefinedForRole(role: Role) : Exception("${role.javaClass}")

