package com.mananasy.voiceList.core.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mananasy.voiceList.feature.history.data.datasource.SingerHistoryDao
import com.mananasy.voiceList.feature.history.data.model.SingerHistoryEntity
import com.mananasy.voiceList.feature.singer.data.datasource.SingerDao
import com.mananasy.voiceList.feature.singer.data.model.SingerEntity

@Database(entities = [SingerEntity::class, SingerHistoryEntity::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class SingerDatabase : RoomDatabase() {

    abstract fun singerDao(): SingerDao
    abstract fun singerHistoryDao(): SingerHistoryDao

    companion object {
        @Volatile
        private var INSTANCE: SingerDatabase? = null

        fun getDatabase(context: Context): SingerDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SingerDatabase::class.java,
                    "singer_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
