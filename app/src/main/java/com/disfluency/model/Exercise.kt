package com.disfluency.model

data class Exercise(
    val id: Long,
    val therapistId: Long, //TODO: cambiar por la referencia al fono
    val title: String,
    val assignment: String,
    val phrase: String
)