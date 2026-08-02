package com.mananasy.voiceList.feature.singer.di

import com.mananasy.voiceList.feature.singer.data.datasource.SingerLocalDataSource
import com.mananasy.voiceList.feature.singer.data.mapper.SingerMapper
import com.mananasy.voiceList.feature.singer.data.repository.SingerRepositoryImpl
import com.mananasy.voiceList.feature.singer.domain.repository.SingerRepository
import com.mananasy.voiceList.feature.singer.domain.usecase.DeleteSingerUseCase
import com.mananasy.voiceList.feature.singer.domain.usecase.GetSingerByIdUseCase
import com.mananasy.voiceList.feature.singer.domain.usecase.InsertSingerUseCase
import com.mananasy.voiceList.feature.singer.domain.usecase.SearchSingersUseCase
import com.mananasy.voiceList.feature.singer.domain.usecase.ToggleFavoriteUseCase
import com.mananasy.voiceList.feature.singer.domain.usecase.UpdateSingerUseCase
import com.mananasy.voiceList.feature.singer.presentation.state.SingerDetailViewModel
import com.mananasy.voiceList.feature.singer.presentation.state.SingerViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val singerModule = module {

    single { SingerMapper() }
    single { SingerLocalDataSource(get()) }
    single<SingerRepository> { SingerRepositoryImpl(get(), get()) }

    factory { GetSingerByIdUseCase(get()) }
    factory { SearchSingersUseCase(get()) }
    factory { InsertSingerUseCase(get()) }
    factory { UpdateSingerUseCase(get()) }
    factory { DeleteSingerUseCase(get()) }
    factory { ToggleFavoriteUseCase(get()) }

    viewModel {
        SingerViewModel(
            searchSingers = get(),
            insertSinger = get(),
            updateSinger = get(),
            deleteSinger = get(),
            toggleFavoriteUseCase = get()
        )
    }
    viewModel { params ->
        SingerDetailViewModel(
            getSingerById = get(),
            updateSinger = get(),
            deleteSinger = get(),
            toggleFavoriteUseCase = get(),
            addToHistory = get(),
            singerId = params.get()
        )
    }
}
