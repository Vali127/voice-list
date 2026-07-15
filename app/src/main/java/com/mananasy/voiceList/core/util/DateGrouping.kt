package com.mananasy.voiceList.core.util

import java.text.SimpleDateFormat
import java.util.*

fun relativeDateLabel(timestampMillis: Long): String {
    val now = Calendar.getInstance()
    val then = Calendar.getInstance().apply { timeInMillis = timestampMillis }

    val isSameDay = now.get(Calendar.YEAR) == then.get(Calendar.YEAR) &&
            now.get(Calendar.DAY_OF_YEAR) == then.get(Calendar.DAY_OF_YEAR)
    if (isSameDay) return "Today"

    val diffDays = ((now.timeInMillis - then.timeInMillis) / (1000 * 60 * 60 * 24)).toInt()
    if (diffDays in 1..6) return "$diffDays days ago"

    val formatter = SimpleDateFormat("d MMMM", Locale.getDefault())
    return formatter.format(Date(timestampMillis))
}

fun relativeTimeLabel(timestampMillis: Long): String {
    val diffMillis = System.currentTimeMillis() - timestampMillis
    val diffMinutes = diffMillis / (1000 * 60)
    val diffHours = diffMinutes / 60

    return when {
        diffMinutes < 1 -> "Just now"
        diffMinutes < 60 -> "$diffMinutes min ago"
        diffHours < 24 -> "${diffHours}h ago"
        else -> SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(timestampMillis))
    }
}