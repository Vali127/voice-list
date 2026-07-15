package com.mananasy.voiceList.feature.singer.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mananasy.voiceList.feature.singer.data.SingerRepository

class SingerViewModelFactory(
    private val repository: SingerRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SingerViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SingerViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}