package com.mananasy.voiceList.feature.singer.presentation.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mananasy.voiceList.feature.history.domain.usecase.AddToHistoryUseCase
import com.mananasy.voiceList.feature.singer.domain.entity.Singer
import com.mananasy.voiceList.feature.singer.domain.usecase.DeleteSingerUseCase
import com.mananasy.voiceList.feature.singer.domain.usecase.GetSingerByIdUseCase
import com.mananasy.voiceList.feature.singer.domain.usecase.ToggleFavoriteUseCase
import com.mananasy.voiceList.feature.singer.domain.usecase.UpdateSingerUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SingerDetailViewModel(
    private val getSingerById: GetSingerByIdUseCase,
    private val updateSinger: UpdateSingerUseCase,
    private val deleteSinger: DeleteSingerUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val addToHistory: AddToHistoryUseCase,
    private val singerId: Int
) : ViewModel() {

    private val _singer = MutableStateFlow<Singer?>(null)
    val singer: StateFlow<Singer?> = _singer

    init {
        viewModelScope.launch {
            _singer.value = getSingerById(singerId)
            addToHistory(singerId)
        }
    }

    fun toggleFavorite() = viewModelScope.launch {
        _singer.value?.let {
            toggleFavoriteUseCase(it.id, !it.isFavorite)
            _singer.value = it.copy(isFavorite = !it.isFavorite)
        }
    }

    fun update(updated: Singer) = viewModelScope.launch {
        updateSinger(updated)
        _singer.value = updated
    }

    fun delete(onDeleted: () -> Unit) = viewModelScope.launch {
        _singer.value?.let {
            deleteSinger(it)
            onDeleted()
        }
    }
}
