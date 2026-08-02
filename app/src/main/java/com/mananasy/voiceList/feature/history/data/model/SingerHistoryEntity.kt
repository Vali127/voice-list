package com.mananasy.voiceList.feature.history.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.mananasy.voiceList.feature.singer.data.model.SingerEntity

@Entity(
    tableName = "singer_history",
    foreignKeys = [
        ForeignKey(
            entity = SingerEntity::class,
            parentColumns = ["id"],
            childColumns = ["singerId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
)
data class SingerHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val singerId: Int,
    val viewedAt: Long,
)
