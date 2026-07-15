package com.mananasy.voiceList.feature.singer.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mananasy.voiceList.feature.singer.data.Singer
import com.mananasy.voiceList.feature.singer.data.SingerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SingerViewModel(private val repository: SingerRepository) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    val allSingers: StateFlow<List<Singer>> = repository.allSingers
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val searchResults: StateFlow<List<Singer>> = _searchQuery
        .flatMapLatest { query -> repository.search(query) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun insert(singer: Singer) = viewModelScope.launch { repository.insert(singer) }
    fun update(singer: Singer) = viewModelScope.launch { repository.update(singer) }
    fun delete(singer: Singer) = viewModelScope.launch { repository.delete(singer) }
    fun toggleFavorite(id: Int, isFavorite: Boolean) =
        viewModelScope.launch { repository.setFavorite(id, isFavorite) }
}