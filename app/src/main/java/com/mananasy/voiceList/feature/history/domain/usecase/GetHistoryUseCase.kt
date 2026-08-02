package com.mananasy.voiceList.feature.history.domain.usecase

import com.mananasy.voiceList.feature.history.domain.entity.HistoryEntry
import com.mananasy.voiceList.feature.history.domain.repository.SingerHistoryRepository
import kotlinx.coroutines.flow.Flow

class GetHistoryUseCase(private val repository: SingerHistoryRepository) {
    operator fun invoke(): Flow<List<HistoryEntry>> = repository.history
}
