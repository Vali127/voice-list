package com.mananasy.voiceList.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mananasy.voiceList.feature.history.data.SingerHistory
import com.mananasy.voiceList.feature.history.data.SingerHistoryDao
import com.mananasy.voiceList.feature.singer.data.Singer
import com.mananasy.voiceList.feature.singer.data.SingerDao

@Database(entities = [Singer::class, SingerHistory::class], version = 2, exportSchema = false)
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