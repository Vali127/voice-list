package com.mananasy.voiceList.feature.singer.domain.usecase

import com.mananasy.voiceList.feature.singer.domain.entity.Singer
import com.mananasy.voiceList.feature.singer.domain.repository.SingerRepository

class DeleteSingerUseCase(private val repository: SingerRepository) {
    suspend operator fun invoke(singer: Singer) = repository.delete(singer)
}
