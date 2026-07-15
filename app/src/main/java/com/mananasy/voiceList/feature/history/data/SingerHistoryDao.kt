package com.mananasy.voiceList.feature.history.data

import androidx.room.Dao
import androidx.room.Embedded
import androidx.room.Insert
import androidx.room.Query
import com.mananasy.voiceList.feature.singer.data.Singer
import kotlinx.coroutines.flow.Flow

data class SingerHistoryEntry(
    @Embedded val singer: Singer,
    val viewedAt: Long
)

@Dao
interface SingerHistoryDao {
    @Insert
    suspend fun insert(history: SingerHistory)

    @Query("""
        SELECT singer.*, singer_history.viewedAt FROM singer
        INNER JOIN singer_history ON singer.id = singer_history.singerId
        ORDER BY singer_history.viewedAt DESC
    """)
    fun getHistory(): Flow<List<SingerHistoryEntry>>

    @Query("DELETE FROM singer_history")
    suspend fun clearHistory()
}