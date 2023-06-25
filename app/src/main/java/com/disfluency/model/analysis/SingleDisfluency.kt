package com.disfluency.model.analysis

data class SingleDisfluency(val disfluencyType: DisfluencyType): Disfluency {
    override fun getDisfluency(): String {
        return disfluencyType.toString()
    }
}