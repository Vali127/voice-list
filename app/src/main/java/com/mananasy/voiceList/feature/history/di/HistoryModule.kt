package com.mananasy.voiceList.feature.history.di

import com.mananasy.voiceList.feature.history.data.datasource.SingerHistoryLocalDataSource
import com.mananasy.voiceList.feature.history.data.mapper.SingerHistoryMapper
import com.mananasy.voiceList.feature.history.data.repository.SingerHistoryRepositoryImpl
import com.mananasy.voiceList.feature.history.domain.repository.SingerHistoryRepository
import com.mananasy.voiceList.feature.history.domain.usecase.AddToHistoryUseCase
import com.mananasy.voiceList.feature.history.domain.usecase.ClearHistoryUseCase
import com.mananasy.voiceList.feature.history.domain.usecase.GetHistoryUseCase
import com.mananasy.voiceList.feature.history.presentation.state.HistoryViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val historyModule = module {

    single { SingerHistoryLocalDataSource(get()) }
    single { SingerHistoryMapper(get()) }
    single<SingerHistoryRepository> { SingerHistoryRepositoryImpl(get(), get()) }

    factory { GetHistoryUseCase(get()) }
    factory { AddToHistoryUseCase(get()) }
    factory { ClearHistoryUseCase(get()) }

    viewModel { HistoryViewModel(get(), get()) }
}
