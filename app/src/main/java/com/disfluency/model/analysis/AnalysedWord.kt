package com.disfluency.model.analysis

data class AnalysedWord(val word: String, val disfluency: Disfluency? = null) {
    fun getDisfluency(): String {
        return disfluency?.let { it.getDisfluency() } ?: ""
    }
}
