package com.mananasy.voiceList.feature.singer.domain.entity

data class Singer(
    val id: Int = 0,
    val name: String,
    val birthDate: String?,
    val photo: String?,
    val description: String?,
    val tags: List<String>,
    val isFavorite: Boolean = false
)
