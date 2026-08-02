package com.mananasy.voiceList.feature.history.domain.usecase

import com.mananasy.voiceList.feature.history.domain.repository.SingerHistoryRepository

class ClearHistoryUseCase(private val repository: SingerHistoryRepository) {
    suspend operator fun invoke() = repository.clearHistory()
}
