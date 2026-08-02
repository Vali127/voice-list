package com.mananasy.voiceList.feature.singer.domain.usecase

import com.mananasy.voiceList.feature.singer.domain.entity.Singer
import com.mananasy.voiceList.feature.singer.domain.repository.SingerRepository

class UpdateSingerUseCase(private val repository: SingerRepository) {
    suspend operator fun invoke(singer: Singer) = repository.update(singer)
}
