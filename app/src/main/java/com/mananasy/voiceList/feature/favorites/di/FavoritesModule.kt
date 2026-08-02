package com.mananasy.voiceList.feature.favorites.di

import com.mananasy.voiceList.feature.favorites.domain.usecase.GetFavoritesUseCase
import com.mananasy.voiceList.feature.favorites.domain.usecase.UpdateFavoriteUseCase
import com.mananasy.voiceList.feature.favorites.presentation.state.FavoritesViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val favoritesModule = module {

    factory { GetFavoritesUseCase(get()) }
    factory { UpdateFavoriteUseCase(get()) }

    viewModel { FavoritesViewModel(get(), get()) }
}
