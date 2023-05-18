package com.disfluency.model

import java.time.LocalDate

data class Exercise(val title: String, val description: String, val phrase: String){
    fun phraseAsQuote(): String{
        return "\"$phrase\""
    }
}

data class ExerciseTask(val exercise: Exercise, val patient: Patient, val date: LocalDate)

data class ExerciseTaskEntry(val exerciseTask: ExerciseTask, val date: LocalDate, val audio: String) //TODO: audio en string por el momento despues vemos