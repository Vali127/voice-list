package com.mananasy.voiceList.feature.history.domain.entity

import com.mananasy.voiceList.feature.singer.domain.entity.Singer

data class HistoryEntry(
    val singer: Singer,
    val viewedAt: Long
)
