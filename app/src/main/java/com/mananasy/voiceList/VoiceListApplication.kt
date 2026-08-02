package com.mananasy.voiceList

import android.app.Application
import com.mananasy.voiceList.core.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class VoiceListApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@VoiceListApplication)
            modules(appModule)
        }
    }
}
