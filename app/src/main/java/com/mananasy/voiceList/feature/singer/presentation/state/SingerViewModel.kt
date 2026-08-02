package com.mananasy.voiceList.feature.singer.presentation.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mananasy.voiceList.feature.singer.domain.entity.Singer
import com.mananasy.voiceList.feature.singer.domain.usecase.DeleteSingerUseCase
import com.mananasy.voiceList.feature.singer.domain.usecase.InsertSingerUseCase
import com.mananasy.voiceList.feature.singer.domain.usecase.SearchSingersUseCase
import com.mananasy.voiceList.feature.singer.domain.usecase.ToggleFavoriteUseCase
import com.mananasy.voiceList.feature.singer.domain.usecase.UpdateSingerUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
class SingerViewModel(
    private val searchSingers: SearchSingersUseCase,
    private val insertSinger: InsertSingerUseCase,
    private val updateSinger: UpdateSingerUseCase,
    private val deleteSinger: DeleteSingerUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    val searchResults: StateFlow<List<Singer>> = _searchQuery
        .flatMapLatest { query -> searchSingers(query) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun insert(singer: Singer) = viewModelScope.launch { insertSinger(singer) }
    fun update(singer: Singer) = viewModelScope.launch { updateSinger(singer) }
    fun delete(singer: Singer) = viewModelScope.launch { deleteSinger(singer) }
    fun toggleFavorite(id: Int, isFavorite: Boolean) =
        viewModelScope.launch { toggleFavoriteUseCase(id, isFavorite) }
}
