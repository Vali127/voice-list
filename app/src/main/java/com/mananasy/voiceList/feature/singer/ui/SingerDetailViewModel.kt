package com.mananasy.voiceList.feature.singer.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mananasy.voiceList.feature.history.data.SingerHistoryRepository
import com.mananasy.voiceList.feature.singer.data.Singer
import com.mananasy.voiceList.feature.singer.data.SingerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SingerDetailViewModel(
    private val repository: SingerRepository,
    private val historyRepository: SingerHistoryRepository,
    private val singerId: Int
) : ViewModel() {

    private val _singer = MutableStateFlow<Singer?>(null)
    val singer: StateFlow<Singer?> = _singer

    init {
        viewModelScope.launch {
            _singer.value = repository.getById(singerId)
            historyRepository.addToHistory(singerId)
        }
    }

    fun toggleFavorite() = viewModelScope.launch {
        _singer.value?.let {
            repository.setFavorite(it.id, !it.isFavorite)
            _singer.value = it.copy(isFavorite = !it.isFavorite)
        }
    }

    fun update(updated: Singer) = viewModelScope.launch {
        repository.update(updated)
        _singer.value = updated
    }

    fun delete(onDeleted: () -> Unit) = viewModelScope.launch {
        _singer.value?.let {
            repository.delete(it)
            onDeleted()
        }
    }
}

class SingerDetailViewModelFactory(
    private val repository: SingerRepository,
    private val historyRepository: SingerHistoryRepository,
    private val singerId: Int
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SingerDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SingerDetailViewModel(repository, historyRepository, singerId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}