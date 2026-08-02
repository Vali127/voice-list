package com.mananasy.voiceList.feature.favorites.presentation.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mananasy.voiceList.feature.favorites.domain.usecase.GetFavoritesUseCase
import com.mananasy.voiceList.feature.favorites.domain.usecase.UpdateFavoriteUseCase
import com.mananasy.voiceList.feature.singer.domain.entity.Singer
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val getFavorites: GetFavoritesUseCase,
    private val updateFavorite: UpdateFavoriteUseCase
) : ViewModel() {

    val favorites: StateFlow<List<Singer>> = getFavorites()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun toggleFavorite(id: Int, isFavorite: Boolean) =
        viewModelScope.launch { updateFavorite(id, isFavorite) }
}
