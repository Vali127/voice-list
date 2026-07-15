package com.mananasy.voiceList.feature.explore.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mananasy.voiceList.core.di.AppContainer
import com.mananasy.voiceList.feature.singer.ui.SingerRow
import com.mananasy.voiceList.feature.singer.ui.SingerViewModel
import com.mananasy.voiceList.feature.singer.ui.SingerViewModelFactory

@Composable
fun ExploreScreen() {
    val context = LocalContext.current
    val container = remember { AppContainer(context) }
    val viewModel: SingerViewModel = viewModel(
        factory = SingerViewModelFactory(container.singerRepository)
    )

    val singers by viewModel.allSingers.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Explore", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(singers) { singer ->
                SingerRow(singer = singer)
            }
        }
    }
}