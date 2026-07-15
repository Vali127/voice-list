package com.mananasy.voiceList.feature.singer.data

import android.content.Context
import com.mananasy.voiceList.core.util.ImageStorage
import kotlinx.coroutines.flow.Flow

class SingerRepository(private val dao: SingerDao, private val context: Context) {
    val allSingers: Flow<List<Singer>> = dao.getAll()
    val favorites: Flow<List<Singer>> = dao.getFavorites()

    suspend fun getById(id: Int): Singer? = dao.getById(id)
    fun search(query: String): Flow<List<Singer>> = dao.search(query)
    suspend fun insert(singer: Singer): Long = dao.insert(singer)
    suspend fun update(singer: Singer) = dao.update(singer)

    suspend fun delete(singer: Singer) {
        ImageStorage.deleteImage(singer.photo)
        dao.delete(singer)
    }

    suspend fun setFavorite(id: Int, isFavorite: Boolean) = dao.setFavorite(id, isFavorite)
}