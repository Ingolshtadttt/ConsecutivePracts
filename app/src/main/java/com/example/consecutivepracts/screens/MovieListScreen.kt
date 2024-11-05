package com.example.consecutivepracts.screens

import androidx.compose.foundation.Image
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Icon
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.clickable
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import coil.compose.rememberAsyncImagePainter
import com.example.androidapp.R
import com.example.consecutivepracts.components.AnimeViewModel
import com.example.consecutivepracts.model.Anime


@Composable
fun ListScreen(viewModel: AnimeViewModel, onClick: (Int) -> Unit) {
    val animeList by viewModel.anime.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val searchResults by viewModel.searchResults.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        SearchBar(
            query = searchQuery,
            onQueryChange = { viewModel.onSearchQueryChange(it) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (searchQuery.isBlank()) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(animeList.take(AnimeViewModel.COUNT_ANIME_ON_PAGE)) { anime ->
                    AnimeListItem(anime = anime, onClick = { onClick(anime.id) })
                }
            }
        } else {
            if (searchResults.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(searchResults) { anime ->
                        AnimeListItem(anime = anime, onClick = { onClick(anime.id) })
                    }
                }
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "${stringResource(R.string.search_not_found_result)} \"$searchQuery\"")
                }
            }
        }
    }
}

@Composable
fun AnimeListItem(anime: Anime, onClick: () -> Unit) {
    ListItem(
        modifier = Modifier
            .clickable { onClick() }
            .padding(8.dp),
        headlineContent = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top,
            ) {
                Image(
                    painter = rememberAsyncImagePainter(anime.fullImageUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .size(170.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column{
                    Text(
                        anime.title,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        anime.synopsis,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 6,
                    )
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = query,
        onValueChange = { onQueryChange(it) },
        label = { Text(text = stringResource(R.string.search_text)) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(R.string.search_icon_description)
            )
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(R.string.clear_search),
                    modifier = Modifier
                        .clickable { onQueryChange("") }
                )
            }
        },
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = MaterialTheme.colorScheme.surface,
            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
            unfocusedIndicatorColor = Color.Transparent,
            focusedLabelColor = MaterialTheme.colorScheme.onSurface,
            unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        ),
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
            .border(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f), MaterialTheme.shapes.medium)
            .shadow(4.dp, shape = MaterialTheme.shapes.medium)
    )
}