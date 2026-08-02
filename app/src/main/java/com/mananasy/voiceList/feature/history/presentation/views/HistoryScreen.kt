package com.mananasy.voiceList.feature.history.presentation.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mananasy.voiceList.core.ui.EmptyState
import com.mananasy.voiceList.core.util.relativeDateLabel
import com.mananasy.voiceList.feature.history.presentation.components.HistoryRow
import com.mananasy.voiceList.feature.history.presentation.state.HistoryViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HistoryScreen(navController: NavHostController) {
    val viewModel: HistoryViewModel = koinViewModel()

    val history by viewModel.history.collectAsState()

    val grouped = history
        .groupBy { relativeDateLabel(it.viewedAt) }
        .mapValues { (_, entries) ->
            entries
                .groupBy { it.singer.id }
                .map { (_, sameSinger) -> sameSinger.maxBy { it.viewedAt } }
                .sortedByDescending { it.viewedAt }
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(start = 16.dp, end = 16.dp, top = 6.dp, bottom = 16.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.History,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "History",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )
        }

        if (history.isEmpty()) {
            EmptyState(
                message = "No history yet",
                icon = Icons.Filled.History,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                grouped.forEach { (label, entries) ->
                    item {
                        Text(
                            text = label,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                            color = MaterialTheme.colorScheme.outline,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                    items(entries) { entry ->
                        HistoryRow(
                            entry = entry,
                            onClick = { navController.navigate("singer_detail/${entry.singer.id}") }
                        )
                    }
                }
            }
        }
    }
}
