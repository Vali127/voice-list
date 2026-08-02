package com.mananasy.voiceList.feature.singer.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "singer")
data class SingerEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val birthDate: String?,
    val photo: String?,
    val description: String?,
    val tags: List<String>,
    val isFavorite: Boolean = false
)
