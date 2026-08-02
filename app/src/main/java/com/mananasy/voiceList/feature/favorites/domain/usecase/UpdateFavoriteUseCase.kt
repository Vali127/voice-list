package com.mananasy.voiceList.feature.favorites.domain.usecase

import com.mananasy.voiceList.feature.singer.domain.repository.SingerRepository

class UpdateFavoriteUseCase(private val repository: SingerRepository) {
    suspend operator fun invoke(id: Int, isFavorite: Boolean) = repository.setFavorite(id, isFavorite)
}
