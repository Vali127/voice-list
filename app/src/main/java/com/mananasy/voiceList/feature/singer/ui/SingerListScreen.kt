package com.mananasy.voiceList.feature.singer.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil3.compose.rememberAsyncImagePainter
import com.mananasy.voiceList.core.di.AppContainer
import com.mananasy.voiceList.core.ui.EditDeleteMenu
import com.mananasy.voiceList.core.ui.EmptyState
import com.mananasy.voiceList.feature.singer.data.Singer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SingerListScreen(navController: NavHostController) {
    val context = LocalContext.current
    val container = remember { AppContainer(context) }
    val viewModel: SingerViewModel = viewModel(
        factory = SingerViewModelFactory(container.singerRepository)
    )

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

@Composable
fun SingerPhoto(photo: String?, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant),
        contentAlignment = Alignment.Center
    ) {
        if (photo != null) {
            Image(
                painter = rememberAsyncImagePainter(model = java.io.File(photo)),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        } else {
            Icon(
                imageVector = Icons.Filled.MusicNote,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SingerCard(
    singer: Singer,
    onClick: () -> Unit,
    onToggleFavorite: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        SingerPhoto(photo = singer.photo, modifier = Modifier.size(90.dp))

        Spacer(modifier = Modifier.width(12.dp))


        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.MusicNote,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = singer.name,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            }


            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                singer.tags.forEach { tag ->
                    SuggestionChip(
                        onClick = {},
                        label = {
                            Text(
                                text = tag,
                                style = MaterialTheme.typography.labelSmall
                            )
                        },
                        modifier = Modifier.height(24.dp)
                    )
                }
            }
        }


        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onToggleFavorite) {
                Icon(
                    imageVector = if (singer.isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = if (singer.isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            EditDeleteMenu(itemName = singer.name, onEdit = onEdit, onDelete = onDelete)
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SingerRow(singer: Singer, onClick: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SingerPhoto(photo = singer.photo, modifier = Modifier.size(56.dp))

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.MusicNote,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = singer.name,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    maxLines = 1
                )
            }

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                singer.tags.forEach { tag ->
                    SuggestionChip(
                        onClick = {},
                        label = {
                            Text(
                                text = tag,
                                style = MaterialTheme.typography.labelSmall
                            )
                        },
                        modifier = Modifier.height(24.dp)
                    )
                }
            }
        }
    }
}