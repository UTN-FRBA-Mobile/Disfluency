package com.disfluency.model

data class Exercise(
    val id: Int,
    val therapistId: Int,
    val title: String,
    val instruction: String,
    val phrase: String? = "",
    val sampleAudioURL: String,
    val number: Int
) {
    fun fullName(): String{
        return "$title $instruction $phrase"
    }

    fun number(): String{
        return "#$number"
    }

    fun getFullInstructions(): String{
        if (phrase.isNullOrBlank()){
            return instruction
        }else{
            return "$instruction: $phrase"
        }
    }

    fun getAudioSample(): Unit{
        return Unit
    }
}