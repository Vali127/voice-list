package com.mananasy.voiceList.feature.singer.data.repository

import com.mananasy.voiceList.core.util.ImageStorage
import com.mananasy.voiceList.feature.singer.data.datasource.SingerLocalDataSource
import com.mananasy.voiceList.feature.singer.data.mapper.SingerMapper
import com.mananasy.voiceList.feature.singer.domain.entity.Singer
import com.mananasy.voiceList.feature.singer.domain.repository.SingerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SingerRepositoryImpl(
    private val localDataSource: SingerLocalDataSource,
    private val mapper: SingerMapper
) : SingerRepository {

    override val allSingers: Flow<List<Singer>> =
        localDataSource.allSingers.map(mapper::toDomainList)

    override val favorites: Flow<List<Singer>> =
        localDataSource.favorites.map(mapper::toDomainList)

    override suspend fun getById(id: Int): Singer? =
        localDataSource.getById(id)?.let(mapper::toDomain)

    override fun search(query: String): Flow<List<Singer>> =
        localDataSource.search(query).map(mapper::toDomainList)

    override suspend fun insert(singer: Singer): Long =
        localDataSource.insert(mapper.toEntity(singer))

    override suspend fun update(singer: Singer) =
        localDataSource.update(mapper.toEntity(singer))

    override suspend fun delete(singer: Singer) {
        ImageStorage.deleteImage(singer.photo)
        localDataSource.delete(mapper.toEntity(singer))
    }

    override suspend fun setFavorite(id: Int, isFavorite: Boolean) =
        localDataSource.setFavorite(id, isFavorite)
}
