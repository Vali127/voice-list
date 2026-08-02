package com.mananasy.voiceList.feature.singer.data.mapper

import com.mananasy.voiceList.feature.singer.data.model.SingerEntity
import com.mananasy.voiceList.feature.singer.domain.entity.Singer

class SingerMapper {

    fun toDomain(entity: SingerEntity): Singer = Singer(
        id = entity.id,
        name = entity.name,
        birthDate = entity.birthDate,
        photo = entity.photo,
        description = entity.description,
        tags = entity.tags,
        isFavorite = entity.isFavorite
    )

    fun toEntity(singer: Singer): SingerEntity = SingerEntity(
        id = singer.id,
        name = singer.name,
        birthDate = singer.birthDate,
        photo = singer.photo,
        description = singer.description,
        tags = singer.tags,
        isFavorite = singer.isFavorite
    )

    fun toDomainList(entities: List<SingerEntity>): List<Singer> = entities.map(::toDomain)
}
