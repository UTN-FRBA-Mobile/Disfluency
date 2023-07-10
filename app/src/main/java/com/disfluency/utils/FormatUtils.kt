package com.disfluency.utils

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.disfluency.R
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.format.TextStyle
import java.util.*

@SuppressLint("SimpleDateFormat")
fun millisecondsAsMinutesAndSeconds(milliseconds: Long): String {
    val format = SimpleDateFormat("mm:ss")
    return format.format(Date(milliseconds))
}

@Composable
fun formatDayOfWeek(dayOfWeek: DayOfWeek): String{
    return dayOfWeek.getDisplayName(TextStyle.FULL, Locale(stringResource(R.string.locale))).replaceFirstChar { it.uppercase() }
}