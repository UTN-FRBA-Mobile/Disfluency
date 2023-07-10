package com.disfluency.model.dto

import com.disfluency.model.*
import com.disfluency.model.Patient
import com.disfluency.model.serialization.DayDeserializer
import com.disfluency.model.serialization.DaySerializer
import com.fasterxml.jackson.annotation.*
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.WRAPPER_OBJECT,
    property = "type")
@JsonSubTypes(
    JsonSubTypes.Type(value = PatientDTO::class, name = "patient"),
    JsonSubTypes.Type(value = PhonoDTO::class, name = "therapist"))
interface RoleDTO {
    fun toRole(): Role
}

data class PatientDTO(
    @JsonProperty("name")
    val name: String,

    @JsonProperty("lastName")
    val lastName: String,

    @JsonProperty("dateOfBirth")
    @JsonDeserialize(using = LocalDateDeserializer::class)
    @JsonSerialize(using = LocalDateSerializer::class)
    val dateOfBirth: LocalDate,

    @JsonProperty("id")
    val id: String,

    @JsonProperty("email")
    val email: String,

    @JsonProperty("joinedSince")
    @JsonDeserialize(using = LocalDateDeserializer::class)
    @JsonSerialize(using = LocalDateSerializer::class)
    val joinedSince: LocalDate,

    @JsonProperty("profilePictureUrl")
    val profilePic: Int,

    @JsonProperty("weeklyTurn")
    @JsonDeserialize(using = DayDeserializer::class)
    @JsonSerialize(using = DaySerializer::class)
    val weeklyTurn: List<DayOfWeek>, //TODO: ver cual seria el tipo de dato para esto

    @JsonProperty("weeklyHour")
    @JsonDeserialize(using = LocalTimeDeserializer::class)
    @JsonSerialize(using = LocalTimeSerializer::class)
    val weeklyHour: LocalTime, //TODO: ver cual seria el tipo de dato para esto
) : RoleDTO {
    override fun toRole(): Role {
        return Patient(name, lastName, dateOfBirth, id, email, joinedSince, profilePic, weeklyTurn, weeklyHour)
    }
}

data class PhonoDTO(
    @JsonProperty("id")
    val id: String,

    @JsonProperty("name")
    val name: String,

    @JsonProperty("lastName")
    val lastName: String,

    @JsonProperty("profilePictureUrl")
    val profilePictureUrl: Int,
): RoleDTO {
    override fun toRole(): Role {
        return Phono(id, name, lastName, profilePictureUrl)
    }
}