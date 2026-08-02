package com.mananasy.voiceList.core.di

import com.mananasy.voiceList.feature.favorites.di.favoritesModule
import com.mananasy.voiceList.feature.history.di.historyModule
import com.mananasy.voiceList.feature.singer.di.singerModule
import org.koin.dsl.module

val appModule = module {
    includes(
        databaseModule,
        singerModule,
        historyModule,
        favoritesModule
    )
}
