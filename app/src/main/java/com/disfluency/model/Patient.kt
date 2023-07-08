package com.disfluency.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.time.Period


data class Patient(
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

    @JsonProperty("profilePic")
    val profilePic: Int,

    @JsonProperty("weeklyTurn")
    @JsonDeserialize(using = DayDeserializer::class)
    @JsonSerialize(using = DaySerializer::class)
    val weeklyTurn: List<DayOfWeek>, //TODO: ver cual seria el tipo de dato para esto

    @JsonProperty("weeklyHour")
    @JsonDeserialize(using = LocalTimeDeserializer::class)
    @JsonSerialize(using = LocalTimeSerializer::class)
    val weeklyHour: LocalTime, //TODO: ver cual seria el tipo de dato para esto

    @JsonProperty("exercises")
    val exercises: MutableList<ExerciseAssignment> = ArrayList()
) : Role {

    fun initials(): String {
        return (name.first().toString() + lastName.first().toString()).uppercase()
    }

    fun fullName(): String {
        return "$name $lastName"
    }

    fun fullNameFormal(): String {
        return "$lastName, $name"
    }

    fun age(): Int {
        return Period.between(
            dateOfBirth,
            LocalDate.now()
        ).years
    }
}

object DayDeserializer : JsonDeserializer<List<DayOfWeek>>() {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): List<DayOfWeek> {
        val node = p.readValueAsTree<JsonNode>()
        return node.map { d -> DayOfWeek.valueOf(d.textValue()) }
    }
}

object DaySerializer : JsonSerializer<List<DayOfWeek>>() {
    override fun serialize(
        value: List<DayOfWeek>,
        gen: JsonGenerator,
        serializers: SerializerProvider
    ) {
        with(gen) {
            writeArray(value.map { d -> d.toString() }.toTypedArray(), 0, value.size)
        }
    }
}