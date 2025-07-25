package com.pelipunto.app.ui.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pelipunto.app.ui.components.LoadingView
import com.pelipunto.app.ui.detail.components.DetailBodyContent
import com.pelipunto.app.ui.detail.components.DetailTopContent

@Composable
fun MovieDetailScreen(
    modifier: Modifier = Modifier,
    movieDetailViewModel: DetailViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit,
    onMovieClick: (Int) -> Unit,
    onActorClick: (Int) -> Unit,
    onSeeAllCastClick: () -> Unit,
    onSeeAllReviewsClick: () -> Unit
) {
    val state by movieDetailViewModel.detailState.collectAsStateWithLifecycle()

    Box(modifier = modifier.fillMaxSize()) {
        state.movieDetail?.let { movieDetail ->
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    DetailTopContent(
                        movieDetail = movieDetail,
                        onNavigateUp = onNavigateUp
                    )
                }

                item {
                    DetailBodyContent(
                        movieDetail = movieDetail,
                        similarMovies = state.movies,
                        onMovieClick = onMovieClick,
                        onActorClick = onActorClick,
                        onSeeAllCastClick = onSeeAllCastClick,
                        onSeeAllReviewsClick = onSeeAllReviewsClick
                    )
                }
            }
        }

        LoadingView(isLoading = state.isLoading)

        AnimatedVisibility(
            visible = state.error != null,
            modifier = Modifier.align(Alignment.Center)
        ) {
            Text(
                text = state.error ?: "Error desconocido",
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}