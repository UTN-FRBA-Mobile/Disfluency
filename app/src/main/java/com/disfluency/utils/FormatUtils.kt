package com.disfluency.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun millisecondsAsMinutesAndSeconds(milliseconds: Long): String {
    val format = SimpleDateFormat("mm:ss")
    return format.format(Date(milliseconds))
}