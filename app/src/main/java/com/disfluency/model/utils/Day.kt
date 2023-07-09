package com.disfluency.model.utils

import com.disfluency.R
import java.time.DayOfWeek

sealed class Day(val stringId: Int, val dayOfWeek: DayOfWeek) {
    object MONDAY: Day(R.string.day1, DayOfWeek.MONDAY)
    object TUESDAY: Day(R.string.day2, DayOfWeek.TUESDAY)
    object WEDNESDAY: Day(R.string.day3, DayOfWeek.WEDNESDAY)
    object THURSDAY: Day(R.string.day4, DayOfWeek.THURSDAY)
    object FRIDAY: Day(R.string.day5, DayOfWeek.FRIDAY)
    object SATURDAY: Day(R.string.day6, DayOfWeek.SATURDAY)
    object SUNDAY: Day(R.string.day7, DayOfWeek.SUNDAY)
}

fun daysOfWeek(): List<Day> {
    return Day::class.nestedClasses.map { it.objectInstance as Day }
}