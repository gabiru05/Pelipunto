package com.pelipunto.app.ui.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pelipunto.app.ui.components.LoadingView
import com.pelipunto.app.ui.detail.components.ReviewItem
import com.pelipunto.app.ui.theme.LargePadding
import com.pelipunto.app.ui.theme.MediumPadding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewsListScreen(
    modifier: Modifier = Modifier,
    detailViewModel: DetailViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit
) {
    val state by detailViewModel.detailState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Reseñas") },
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
                    items(it.reviews) { review ->
                        ReviewItem(review = review)
                        Divider(modifier = Modifier.padding(top = MediumPadding))
                    }
                }
            }
            LoadingView(isLoading = state.isLoading)
        }
    }
}