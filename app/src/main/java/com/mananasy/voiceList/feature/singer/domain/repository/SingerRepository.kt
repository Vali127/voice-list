package com.mananasy.voiceList.feature.singer.domain.repository

import com.mananasy.voiceList.feature.singer.domain.entity.Singer
import kotlinx.coroutines.flow.Flow

interface SingerRepository {
    val allSingers: Flow<List<Singer>>
    val favorites: Flow<List<Singer>>

    suspend fun getById(id: Int): Singer?
    fun search(query: String): Flow<List<Singer>>
    suspend fun insert(singer: Singer): Long
    suspend fun update(singer: Singer)
    suspend fun delete(singer: Singer)
    suspend fun setFavorite(id: Int, isFavorite: Boolean)
}
