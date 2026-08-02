package com.mananasy.voiceList.feature.history.presentation.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mananasy.voiceList.feature.history.domain.entity.HistoryEntry
import com.mananasy.voiceList.feature.history.domain.usecase.ClearHistoryUseCase
import com.mananasy.voiceList.feature.history.domain.usecase.GetHistoryUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val getHistory: GetHistoryUseCase,
    private val clearHistoryUseCase: ClearHistoryUseCase
) : ViewModel() {

    val history: StateFlow<List<HistoryEntry>> = getHistory()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun clearHistory() = viewModelScope.launch { clearHistoryUseCase() }
}
