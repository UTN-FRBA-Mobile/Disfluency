package com.disfluency.model.utils

import com.disfluency.R

sealed class DayOfWeek(val stringId: Int) {
    object MONDAY: DayOfWeek(R.string.day1)
    object TUESDAY: DayOfWeek(R.string.day2)
    object WEDNESDAY: DayOfWeek(R.string.day3)
    object THURSDAY: DayOfWeek(R.string.day4)
    object FRIDAY: DayOfWeek(R.string.day5)
    object SATHURDAY: DayOfWeek(R.string.day6)
    object SUNDAY: DayOfWeek(R.string.day7)
    companion object {
        fun values(): List<DayOfWeek> {
            return DayOfWeek::class.nestedClasses.map { it.objectInstance as DayOfWeek }
        }
    }
}