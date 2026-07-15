package com.mananasy.voiceList.feature.singer.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SingerDao {
    @Insert
    suspend fun insert(singer: Singer): Long

    @Update
    suspend fun update(singer: Singer)

    @Delete
    suspend fun delete(singer: Singer)

    @Query("SELECT * FROM singer ORDER BY name ASC")
    fun getAll(): Flow<List<Singer>>

    @Query("SELECT * FROM singer WHERE id = :id")
    suspend fun getById(id: Int): Singer?

    @Query("SELECT * FROM singer WHERE name LIKE '%' || :query || '%' OR tags LIKE '%' || :query || '%'")
    fun search(query: String): Flow<List<Singer>>

    @Query("SELECT * FROM singer WHERE isFavorite = 1 ORDER BY name ASC")
    fun getFavorites(): Flow<List<Singer>>

    @Query("UPDATE singer SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun setFavorite(id: Int, isFavorite: Boolean)
}