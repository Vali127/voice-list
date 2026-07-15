package com.mananasy.voiceList.feature.history.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.mananasy.voiceList.feature.singer.data.Singer

@Entity(
    tableName = "singer_history",
    foreignKeys = [
        ForeignKey(
            entity = Singer::class,
            parentColumns = ["id"],
            childColumns = ["singerId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
)
data class SingerHistory(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val singerId: Int,
    val viewedAt: Long,
)