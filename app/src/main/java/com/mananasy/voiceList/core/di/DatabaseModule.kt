package com.mananasy.voiceList.core.di

import com.mananasy.voiceList.core.database.SingerDatabase
import com.mananasy.voiceList.feature.history.data.datasource.SingerHistoryDao
import com.mananasy.voiceList.feature.singer.data.datasource.SingerDao
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single { SingerDatabase.getDatabase(androidContext()) }
    single { get<SingerDatabase>().singerDao() }
    single { get<SingerDatabase>().singerHistoryDao() }
}
