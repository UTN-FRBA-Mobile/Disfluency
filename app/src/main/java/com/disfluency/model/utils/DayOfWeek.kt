package com.disfluency.model.utils

import androidx.compose.ui.res.stringResource
import com.disfluency.R

sealed class DayOfWeek(val stringId: Int) {
    object MONDAY: DayOfWeek(R.string.day1)
    object TUESDAY: DayOfWeek(R.string.day2)
    object WEDNESDAY: DayOfWeek(R.string.day3)
    object THURSDAY: DayOfWeek(R.string.day4)
    object FRIDAY: DayOfWeek(R.string.day5)
    object SATURDAY: DayOfWeek(R.string.day6)
    object SUNDAY: DayOfWeek(R.string.day7)
}

fun workingDays(): List<DayOfWeek> {
    return listOf(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY)
}