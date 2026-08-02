package com.mananasy.voiceList.core.database

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromTagList(tags: List<String>): String = tags.joinToString(",")

    @TypeConverter
    fun toTagList(data: String): List<String> =
        if (data.isBlank()) emptyList() else data.split(",")
}
