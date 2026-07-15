package com.mananasy.voiceList.feature.history.data

import kotlinx.coroutines.flow.Flow

class SingerHistoryRepository(private val dao: SingerHistoryDao) {
    val history: Flow<List<SingerHistoryEntry>> = dao.getHistory()

    suspend fun addToHistory(singerId: Int) =
        dao.insert(SingerHistory(singerId = singerId, viewedAt = System.currentTimeMillis()))

    suspend fun clearHistory() = dao.clearHistory()
}