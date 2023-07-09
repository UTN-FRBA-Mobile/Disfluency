package com.disfluency.model

interface Role

fun getProfilePicFromRole(role: Role): Int{
    return when (role) {
        is Patient -> role.profilePictureUrl
        is Phono -> role.profilePictureUrl
        else -> { throw NoPictureDefinedForRole(role)}
    }
}

class NoPictureDefinedForRole(role: Role) : Exception("${role.javaClass}")

data class User(val username: String, val password: String, val role: Role){
    fun profilePic(): Int{
        return getProfilePicFromRole(role)
    }
}