package com.mananasy.voiceList.feature.singer.data.datasource

import androidx.room.Delete
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.mananasy.voiceList.feature.singer.data.model.SingerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SingerDao {
    @Insert
    suspend fun insert(singer: SingerEntity): Long

    @Update
    suspend fun update(singer: SingerEntity)

    @Delete
    suspend fun delete(singer: SingerEntity)

    @Query("SELECT * FROM singer ORDER BY name ASC")
    fun getAll(): Flow<List<SingerEntity>>

    @Query("SELECT * FROM singer WHERE id = :id")
    suspend fun getById(id: Int): SingerEntity?

    @Query("SELECT * FROM singer WHERE name LIKE '%' || :query || '%' OR tags LIKE '%' || :query || '%'")
    fun search(query: String): Flow<List<SingerEntity>>

    @Query("SELECT * FROM singer WHERE isFavorite = 1 ORDER BY name ASC")
    fun getFavorites(): Flow<List<SingerEntity>>

    @Query("UPDATE singer SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun setFavorite(id: Int, isFavorite: Boolean)
}
