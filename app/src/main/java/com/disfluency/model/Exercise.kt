package com.disfluency.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Exercise(
    @JsonProperty("id")
    val id: String,

    @JsonProperty("title")
    val title: String,

    @JsonProperty("instruction")
    val instruction: String,

    @JsonProperty("phrase")
    val phrase: String? = null,

    @JsonProperty("sampleRecordingUrl")
    val sampleRecordingUrl: String
) {
    fun fullName(): String{
        return "$title $instruction $phrase"
    }

    fun getFullInstructions(): String{
        return if (phrase.isNullOrBlank()){
            instruction
        }else{
            "$instruction: $phrase"
        }
    }

    fun getAudioSample() {
    }
}
