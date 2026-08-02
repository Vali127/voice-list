package com.mananasy.voiceList.feature.history.data.mapper

import com.mananasy.voiceList.feature.history.data.model.SingerHistoryEntryEntity
import com.mananasy.voiceList.feature.history.domain.entity.HistoryEntry
import com.mananasy.voiceList.feature.singer.data.mapper.SingerMapper

class SingerHistoryMapper(private val singerMapper: SingerMapper) {

    fun toDomain(entry: SingerHistoryEntryEntity): HistoryEntry = HistoryEntry(
        singer = singerMapper.toDomain(entry.singer),
        viewedAt = entry.viewedAt
    )

    fun toDomainList(entries: List<SingerHistoryEntryEntity>): List<HistoryEntry> =
        entries.map(::toDomain)
}
