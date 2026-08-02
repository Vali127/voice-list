package com.mananasy.voiceList.feature.history.domain.repository

import com.mananasy.voiceList.feature.history.domain.entity.HistoryEntry
import kotlinx.coroutines.flow.Flow

interface SingerHistoryRepository {
    val history: Flow<List<HistoryEntry>>

    suspend fun addToHistory(singerId: Int)
    suspend fun clearHistory()
}
