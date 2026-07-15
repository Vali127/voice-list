package com.mananasy.voiceList.feature.singer.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun SingerDetailScreen(singerId: Int, navController: NavHostController) {
    val context = LocalContext.current
    val container = remember { AppContainer(context) }
    val viewModel: SingerDetailViewModel = viewModel(
        factory = SingerDetailViewModelFactory(
            container.singerRepository,
            container.historyRepository,
            singerId
        )
    )

    val singer by viewModel.singer.collectAsState()
    var descriptionExpanded by remember { mutableStateOf(false) }
    var showSheet by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    val topBarAlpha by remember {
        derivedStateOf {
            val scrollThreshold = 500f
            if (scrollThreshold > 0f) {
                (scrollState.value / scrollThreshold).coerceIn(0f, 1f)
            } else {
                0f
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            singer?.let {
                ExtendedFloatingActionButton(
                    onClick = { showSheet = true },
                    icon = { Icon(Icons.Filled.Edit, contentDescription = "Edit") },
                    text = { Text("Edit") }
                )
            }
        },
        containerColor = Color.Transparent
    ) { _ ->
        Box(modifier = Modifier.fillMaxSize()) {
            singer?.let { s ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(16f / 9f)
                        ) {
                            s.photo?.let { photo ->
                                Image(
                                    painter = rememberAsyncImagePainter(photo),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .blur(24.dp),
                                    contentScale = ContentScale.Crop
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        Brush.verticalGradient(
                                            colors = listOf(
                                                Color.Transparent,
                                                MaterialTheme.colorScheme.background
                                            )
                                        )
                                    )
                            )
                        }

                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(start = 16.dp, bottom = 0.dp)
                                .offset(y = 40.dp)
                                .size(120.dp)
                                .border(
                                    width = 4.dp,
                                    color = MaterialTheme.colorScheme.background,
                                    shape = RoundedCornerShape(16.dp)
                                )
                        ) {
                            SingerPhoto(
                                photo = s.photo,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(56.dp))

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .padding(bottom = 32.dp)
                    ) {
                        Text(s.name, style = MaterialTheme.typography.headlineMedium)

                        Spacer(modifier = Modifier.height(12.dp))

                        FilledTonalButton(onClick = { viewModel.toggleFavorite() }) {
                            Icon(
                                imageVector = if (s.isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Favorites")
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Description",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                            )
                            TextButton(onClick = { descriptionExpanded = !descriptionExpanded }) {
                                Text(if (descriptionExpanded) "Less" else "More")
                            }
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    MaterialTheme.colorScheme.surfaceVariant,
                                    RoundedCornerShape(12.dp)
                                )
                                .padding(12.dp)
                        ) {
                            Text(
                                text = s.description ?: "",
                                style = MaterialTheme.typography.bodyLarge,
                                maxLines = if (descriptionExpanded) Int.MAX_VALUE else 4
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        FlowRowTags(tags = s.tags)
                    }
                }
            } ?: run {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            val solidColor = MaterialTheme.colorScheme.background
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                solidColor.copy(alpha = topBarAlpha.coerceAtLeast(0.5f)),
                                solidColor.copy(alpha = topBarAlpha)
                            )
                        )
                    )
            ) {
                TopAppBar(
                    title = {
                        singer?.name?.let { name ->
                            Text(
                                text = name,
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = topBarAlpha)
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    },
                    actions = {
                        singer?.let { s ->
                            EditDeleteMenu(
                                itemName = s.name,
                                onEdit = { showSheet = true },
                                onDelete = { viewModel.delete { navController.popBackStack() } }
                            )
                        }
                    },
                    windowInsets = WindowInsets(0, 0, 0, 0),
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent
                    )
                )
            }
        }
    }

    if (showSheet) {
        singer?.let { s ->
            SingerFormSheet(
                singer = s,
                isNew = false,
                onDismiss = { showSheet = false },
                onSave = { updated ->
                    viewModel.update(updated)
                    showSheet = false
                }
            )
        }
    }
}

@ExperimentalLayoutApi
@Composable
fun FlowRowTags(tags: List<String>) {
    androidx.compose.foundation.layout.FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        tags.forEach { tag ->
            Spacer(modifier = Modifier.width(0.dp))
            AssistChip(onClick = {}, label = { Text(tag) })
        }
    }
}