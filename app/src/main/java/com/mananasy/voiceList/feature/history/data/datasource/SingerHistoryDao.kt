package com.mananasy.voiceList.feature.history.data.datasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mananasy.voiceList.feature.history.data.model.SingerHistoryEntity
import com.mananasy.voiceList.feature.history.data.model.SingerHistoryEntryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SingerHistoryDao {
    @Insert
    suspend fun insert(history: SingerHistoryEntity)

    @Query("""
        SELECT singer.*, singer_history.viewedAt FROM singer
        INNER JOIN singer_history ON singer.id = singer_history.singerId
        ORDER BY singer_history.viewedAt DESC
    """)
    fun getHistory(): Flow<List<SingerHistoryEntryEntity>>

    @Query("DELETE FROM singer_history")
    suspend fun clearHistory()
}
