package com.mananasy.voiceList.feature.history.data.model

import androidx.room.Embedded
import com.mananasy.voiceList.feature.singer.data.model.SingerEntity

data class SingerHistoryEntryEntity(
    @Embedded val singer: SingerEntity,
    val viewedAt: Long
)
