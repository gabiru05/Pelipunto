package com.pelipunto.app.ui.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.pelipunto.app.movie_detail.domain.models.Cast
import com.pelipunto.app.ui.components.LoadingView
import com.pelipunto.app.ui.theme.LargePadding
import com.pelipunto.app.ui.theme.MediumPadding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CastListScreen(
    modifier: Modifier = Modifier,
    detailViewModel: DetailViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit
) {
    val state by detailViewModel.detailState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Elenco Principal") },
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            state.movieDetail?.let {
                LazyColumn(
                    contentPadding = PaddingValues(LargePadding),
                    verticalArrangement = Arrangement.spacedBy(MediumPadding)
                ) {
                    items(it.cast) { actor ->
                        ActorItem(actor = actor)
                    }
                }
            }
            LoadingView(isLoading = state.isLoading)
        }
    }
}

@Composable
private fun ActorItem(actor: Cast) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(MediumPadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MediumPadding)
        ) {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w185${actor.profilePath}",
                contentDescription = actor.name,
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Column {
                Text(text = actor.name, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
                Text(text = actor.character, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}