package com.mananasy.voiceList.core.util

import java.text.SimpleDateFormat
import java.util.*

private const val STORED_DATE_PATTERN = "yyyy-MM-dd"
private val UTC: TimeZone = TimeZone.getTimeZone("UTC")

fun dateMillisToStoredString(millis: Long): String =
    SimpleDateFormat(STORED_DATE_PATTERN, Locale.US)
        .apply { timeZone = UTC }
        .format(Date(millis))

fun storedStringToDateMillis(value: String?): Long? {
    if (value.isNullOrBlank()) return null
    return try {
        SimpleDateFormat(STORED_DATE_PATTERN, Locale.US)
            .apply {
                timeZone = UTC
                isLenient = false
            }
            .parse(value)
            ?.time
    } catch (e: Exception) {
        null
    }
}

fun displayBirthDate(value: String?): String? {
    val millis = storedStringToDateMillis(value) ?: return null
    return SimpleDateFormat("d MMM yyyy", Locale.getDefault())
        .apply { timeZone = UTC }
        .format(Date(millis))
}