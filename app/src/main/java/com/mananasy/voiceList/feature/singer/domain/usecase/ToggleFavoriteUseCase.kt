package com.mananasy.voiceList.feature.singer.domain.usecase

import com.mananasy.voiceList.feature.singer.domain.repository.SingerRepository

class ToggleFavoriteUseCase(private val repository: SingerRepository) {
    suspend operator fun invoke(id: Int, isFavorite: Boolean) = repository.setFavorite(id, isFavorite)
}
