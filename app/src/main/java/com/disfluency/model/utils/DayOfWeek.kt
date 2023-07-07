package com.disfluency.model.utils

import java.time.DayOfWeek

fun daysOfWeek(): List<DayOfWeek> {
    return DayOfWeek::class.nestedClasses.map { it.objectInstance as DayOfWeek }
}