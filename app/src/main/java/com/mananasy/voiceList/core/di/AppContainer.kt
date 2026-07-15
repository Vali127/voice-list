package com.mananasy.voiceList.core.di

import android.content.Context
import com.mananasy.voiceList.data.local.SingerDatabase
import com.mananasy.voiceList.feature.singer.data.SingerRepository
import com.mananasy.voiceList.feature.history.data.SingerHistoryRepository

class AppContainer(context: Context) {
    private val database = SingerDatabase.getDatabase(context)

    val singerRepository: SingerRepository by lazy { SingerRepository(database.singerDao()) }
    val historyRepository: SingerHistoryRepository by lazy { SingerHistoryRepository(database.singerHistoryDao()) }
}