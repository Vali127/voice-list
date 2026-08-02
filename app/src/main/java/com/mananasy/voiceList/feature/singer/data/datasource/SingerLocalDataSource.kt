package com.mananasy.voiceList.feature.singer.data.datasource

import com.mananasy.voiceList.feature.singer.data.model.SingerEntity
import kotlinx.coroutines.flow.Flow

class SingerLocalDataSource(private val dao: SingerDao) {

    val allSingers: Flow<List<SingerEntity>> = dao.getAll()
    val favorites: Flow<List<SingerEntity>> = dao.getFavorites()

    suspend fun getById(id: Int): SingerEntity? = dao.getById(id)

    fun search(query: String): Flow<List<SingerEntity>> = dao.search(query)

    suspend fun insert(singer: SingerEntity): Long = dao.insert(singer)

    suspend fun update(singer: SingerEntity) = dao.update(singer)

    suspend fun delete(singer: SingerEntity) = dao.delete(singer)

    suspend fun setFavorite(id: Int, isFavorite: Boolean) = dao.setFavorite(id, isFavorite)
}
