package com.mananasy.voiceList.feature.singer.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "singer")
data class Singer(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val birthDate: String?,
    val photo: String?,
    val description: String?,
    val tags: List<String>,
    val isFavorite : Boolean = false
)