package com.mananasy.voiceList.feature.history.domain.usecase

import com.mananasy.voiceList.feature.history.domain.repository.SingerHistoryRepository

class AddToHistoryUseCase(private val repository: SingerHistoryRepository) {
    suspend operator fun invoke(singerId: Int) = repository.addToHistory(singerId)
}
