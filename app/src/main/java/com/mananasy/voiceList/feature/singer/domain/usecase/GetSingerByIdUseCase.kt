package com.mananasy.voiceList.feature.singer.domain.usecase

import com.mananasy.voiceList.feature.singer.domain.entity.Singer
import com.mananasy.voiceList.feature.singer.domain.repository.SingerRepository
import kotlinx.coroutines.flow.Flow

class GetSingerByIdUseCase(private val repository: SingerRepository) {
    suspend operator fun invoke(id: Int): Singer? = repository.getById(id)
}
