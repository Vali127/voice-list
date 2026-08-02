package com.mananasy.voiceList.feature.history.data.datasource

import com.mananasy.voiceList.feature.history.data.model.SingerHistoryEntity
import com.mananasy.voiceList.feature.history.data.model.SingerHistoryEntryEntity
import kotlinx.coroutines.flow.Flow

class SingerHistoryLocalDataSource(private val dao: SingerHistoryDao) {

    val history: Flow<List<SingerHistoryEntryEntity>> = dao.getHistory()

    suspend fun addToHistory(singerId: Int) =
        dao.insert(SingerHistoryEntity(singerId = singerId, viewedAt = System.currentTimeMillis()))

    suspend fun clearHistory() = dao.clearHistory()
}
