package com.mananasy.voiceList.feature.history.data.repository

import com.mananasy.voiceList.feature.history.data.datasource.SingerHistoryLocalDataSource
import com.mananasy.voiceList.feature.history.data.mapper.SingerHistoryMapper
import com.mananasy.voiceList.feature.history.domain.entity.HistoryEntry
import com.mananasy.voiceList.feature.history.domain.repository.SingerHistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SingerHistoryRepositoryImpl(
    private val localDataSource: SingerHistoryLocalDataSource,
    private val mapper: SingerHistoryMapper
) : SingerHistoryRepository {

    override val history: Flow<List<HistoryEntry>> =
        localDataSource.history.map(mapper::toDomainList)

    override suspend fun addToHistory(singerId: Int) = localDataSource.addToHistory(singerId)

    override suspend fun clearHistory() = localDataSource.clearHistory()
}
