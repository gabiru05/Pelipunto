package com.pelipunto.app.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.pelipunto.app.movie.domain.models.Movie
import com.pelipunto.app.ui.components.LoadingView
import com.pelipunto.app.ui.home.components.MovieCoverImage
import com.pelipunto.app.ui.home.components.TopContent
import com.pelipunto.app.ui.theme.LargePadding
import com.pelipunto.app.ui.theme.MediumPadding
import com.pelipunto.app.ui.theme.itemSpacing
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

// Función lerp para animaciones
fun lerp(start: Float, stop: Float, fraction: Float): Float {
    return (1 - fraction) * start + fraction * stop
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier, // Este modifier ya contiene el padding del Scaffold principal
    homeViewModel: HomeViewModel = hiltViewModel(),
    onMovieClick: (id: Int) -> Unit
) {
    val state by homeViewModel.homeState.collectAsStateWithLifecycle()
    val pagerState = rememberPagerState(pageCount = { state.discoverMovies.size })
    val isDragged by pagerState.interactionSource.collectIsDraggedAsState()
    val coroutineScope = rememberCoroutineScope()

    // Lógica de auto-scroll del carrusel
    LaunchedEffect(key1 = isDragged) {
        if (!isDragged && state.discoverMovies.isNotEmpty()) {
            while (true) {
                delay(5000)
                val nextPage = (pagerState.currentPage + 1) % state.discoverMovies.size
                pagerState.animateScrollToPage(nextPage)
            }
        }
    }

    // Efecto para manejar el ciclo de vida y reactivar clics
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                coroutineScope.launch {
                    pagerState.scrollToPage(pagerState.currentPage)
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        if (!state.isLoading && state.error == null) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                // No se necesita contentPadding aquí, el modifier del Box ya se encarga
                verticalArrangement = Arrangement.spacedBy(LargePadding)
            ) {
                // --- SECCIÓN CARRUSEL ---
                item {
                    CarouselSection(
                        pagerState = pagerState,
                        movies = state.discoverMovies,
                        onMovieClick = onMovieClick
                    )
                }

                // --- SECCIÓN PELÍCULAS EN TENDENCIA ---
                if (state.trendingMovies.isNotEmpty()) {
                    item {
                        TrendingSection(
                            movies = state.trendingMovies,
                            onMovieClick = onMovieClick
                        )
                    }
                }
            }
        }

        // Vistas de Carga y Error
        LoadingView(
            isLoading = state.isLoading,
            modifier = Modifier.align(Alignment.Center)
        )

        AnimatedVisibility(
            visible = state.error != null,
            modifier = Modifier.align(Alignment.Center).padding(LargePadding)
        ) {
            Text(
                text = state.error ?: "Error desconocido",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun CarouselSection(
    pagerState: PagerState,
    movies: List<Movie>,
    onMovieClick: (id: Int) -> Unit
) {
    val contentPadding = PaddingValues(horizontal = LargePadding * 2)

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        if (movies.isNotEmpty()) {
            HorizontalPager(
                state = pagerState,
                contentPadding = contentPadding,
                pageSpacing = MediumPadding
            ) { page ->
                val movie = movies[page]
                TopContent(
                    movie = movie,
                    onMovieClick = onMovieClick,
                    modifier = Modifier.graphicsLayer {
                        val pageOffset = ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue
                        alpha = lerp(start = 0.5f, stop = 1f, fraction = 1f - pageOffset.coerceIn(0f, 1f))
                        scaleY = lerp(start = 0.85f, stop = 1f, fraction = 1f - pageOffset.coerceIn(0f, 1f))
                    }
                )
            }

            Spacer(modifier = Modifier.height(MediumPadding))

            HorizontalPagerIndicator(
                pagerState = pagerState,
                pageCount = movies.size,
                modifier = Modifier.padding(vertical = itemSpacing),
                activeColor = MaterialTheme.colorScheme.primary,
                inactiveColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
            )
        }
    }
}

@Composable
private fun TrendingSection(
    movies: List<Movie>,
    onMovieClick: (id: Int) -> Unit
) {
    Column {
        SectionTitle(title = "Tendencias", modifier = Modifier.padding(horizontal = LargePadding))
        LazyRow(
            contentPadding = PaddingValues(horizontal = LargePadding),
            horizontalArrangement = Arrangement.spacedBy(MediumPadding)
        ) {
            items(movies, key = { it.id }) { movie ->
                MovieCoverImage(movie = movie, onMovieClick = onMovieClick)
            }
        }
    }
}

@Composable
private fun SectionTitle(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = itemSpacing)
    )
}
