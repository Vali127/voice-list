package com.mananasy.voiceList.feature.singer.presentation.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mananasy.voiceList.core.ui.EmptyState
import com.mananasy.voiceList.feature.singer.domain.entity.Singer
import com.mananasy.voiceList.feature.singer.presentation.components.SingerCard
import com.mananasy.voiceList.feature.singer.presentation.components.SingerFormSheet
import com.mananasy.voiceList.feature.singer.presentation.state.SingerViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SingerListScreen(navController: NavHostController) {
    val viewModel: SingerViewModel = koinViewModel()

    val query by viewModel.searchQuery.collectAsState()
    val results by viewModel.searchResults.collectAsState()

    var selectedTag by remember { mutableStateOf<String?>(null) }
    val availableTags = results.flatMap { it.tags }.distinct().sorted()
    val filteredResults = if (selectedTag != null) results.filter { it.tags.contains(selectedTag) } else results

    var editingSinger by remember { mutableStateOf<Singer?>(null) }
    var showSheet by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    editingSinger = Singer(name = "", birthDate = null, photo = null, description = null, tags = emptyList())
                    showSheet = true
                },
                icon = { Icon(Icons.Filled.Add, contentDescription = null) },
                text = { Text("Add") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 16.dp,
                    bottom = padding.calculateBottomPadding()
                )
        ) {
            OutlinedTextField(
                value = query,
                onValueChange = { viewModel.onSearchQueryChange(it) },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Search singer") },
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
                trailingIcon = { Icon(Icons.Filled.MoreVert, contentDescription = null) },
                shape = RoundedCornerShape(28.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (availableTags.isNotEmpty()) {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(availableTags) { tag ->
                        FilterChip(
                            selected = selectedTag == tag,
                            onClick = { selectedTag = if (selectedTag == tag) null else tag },
                            label = { Text(tag) }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            if (filteredResults.isEmpty()) {
                val message = if (query.isNotEmpty() || selectedTag != null) {
                    "No singers match your search"
                } else {
                    "No singers added yet"
                }
                EmptyState(
                    message = message,
                    icon = Icons.Filled.MusicNote,
                    modifier = Modifier.weight(1f)
                )
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    items(filteredResults) { singer ->
                        SingerCard(
                            singer = singer,
                            onClick = { navController.navigate("singer_detail/${singer.id}") },
                            onToggleFavorite = { viewModel.toggleFavorite(singer.id, !singer.isFavorite) },
                            onEdit = {
                                editingSinger = singer
                                showSheet = true
                            },
                            onDelete = { viewModel.delete(singer) }
                        )
                    }
                }
            }
        }
    }

    if (showSheet && editingSinger != null) {
        SingerFormSheet(
            singer = editingSinger!!,
            isNew = editingSinger!!.id == 0,
            onDismiss = { showSheet = false },
            onSave = { updated ->
                if (updated.id == 0) viewModel.insert(updated) else viewModel.update(updated)
                showSheet = false
            }
        )
    }
}
