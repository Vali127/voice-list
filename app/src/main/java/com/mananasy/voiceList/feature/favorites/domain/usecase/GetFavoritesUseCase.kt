package com.mananasy.voiceList.feature.favorites.domain.usecase

import com.mananasy.voiceList.feature.singer.domain.entity.Singer
import com.mananasy.voiceList.feature.singer.domain.repository.SingerRepository
import kotlinx.coroutines.flow.Flow

class GetFavoritesUseCase(private val repository: SingerRepository) {
    operator fun invoke(): Flow<List<Singer>> = repository.favorites
}
