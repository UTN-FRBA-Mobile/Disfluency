package com.disfluency.model.analysis

data class CompositeDisfluency(val multipleDisfluency: List<DisfluencyType>): Disfluency {
    override fun getDisfluency(): String {
        return multipleDisfluency.joinToString(prefix = "[", separator = "+", postfix = "]")
    }
}
