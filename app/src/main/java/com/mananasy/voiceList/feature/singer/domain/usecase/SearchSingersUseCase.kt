package com.mananasy.voiceList.feature.singer.domain.usecase

import com.mananasy.voiceList.feature.singer.domain.entity.Singer
import com.mananasy.voiceList.feature.singer.domain.repository.SingerRepository
import kotlinx.coroutines.flow.Flow

class SearchSingersUseCase(private val repository: SingerRepository) {
    operator fun invoke(query: String): Flow<List<Singer>> = repository.search(query)
}
