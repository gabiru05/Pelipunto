package com.pelipunto.app.ui.detail

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.firebase.auth.FirebaseAuth
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
    val trailerKey by movieDetailViewModel.trailerKeyState.collectAsStateWithLifecycle()
    var showReviewDialog by remember { mutableStateOf(false) }
    val isLoggedIn by remember { mutableStateOf(FirebaseAuth.getInstance().currentUser != null) }

    Box(modifier = modifier.fillMaxSize()) {
        Scaffold(
            floatingActionButton = {
                if (state.movieDetail != null && isLoggedIn) {
                    FloatingActionButton(onClick = { showReviewDialog = true }) {
                        Icon(Icons.Default.Edit, contentDescription = "Escribir reseña")
                    }
                }
            },
            containerColor = Color.Transparent
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
                state.movieDetail?.let { movieDetail ->
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        item {
                            DetailTopContent(
                                movieDetail = movieDetail,
                                onNavigateUp = onNavigateUp,
                                onWatchTrailerClick = { movieDetailViewModel.fetchTrailer() }
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
                AnimatedVisibility(visible = state.error != null, modifier = Modifier.align(Alignment.Center)) {
                    Text(text = state.error ?: "Error desconocido", color = MaterialTheme.colorScheme.error)
                }
            }
        }
    }

    if (showReviewDialog) {
        ReviewInputDialog(
            onDismiss = { showReviewDialog = false },
            onSubmit = { rating, comment ->
                movieDetailViewModel.postReview(rating, comment)
                showReviewDialog = false
            }
        )
    }

    if (trailerKey != null) {
        VideoPlayer(
            youtubeVideoId = trailerKey!!,
            onDismiss = { movieDetailViewModel.onTrailerDismissed() }
        )
    }
}
@SuppressLint("SetJavaScriptEnabled")
@Composable
fun VideoPlayer(youtubeVideoId: String, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f)
        ) {
            AndroidView(factory = { context ->
                WebView(context).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    webViewClient = WebViewClient()
                    settings.javaScriptEnabled = true
                    val htmlData = """
                        <body style="margin:0px;padding:0px;background-color:black;">
                           <iframe 
                                width="100%" 
                                height="100%" 
                                src="https://www.youtube.com/embed/$youtubeVideoId?autoplay=1&fs=0&modestbranding=1" 
                                frameborder="0" 
                                allow="autoplay; encrypted-media"
                                allowfullscreen>
                           </iframe>
                        </body>
                    """.trimIndent()
                    loadData(htmlData, "text/html", "utf-8")
                }
            })
        }
    }
}

@Composable
private fun ReviewInputDialog(
    onDismiss: () -> Unit,
    onSubmit: (rating: Float, comment: String) -> Unit
) {
    var rating by remember { mutableFloatStateOf(7.0f) }
    var comment by remember { mutableStateOf("") }
    val isSubmitEnabled by remember(comment) { derivedStateOf { comment.isNotBlank() } }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("¿Qué te pareció la película?") },
        text = {
            Column {
                Text(
                    text = "Tu calificación: ${String.format("%.1f", rating)} / 10",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Slider(
                    value = rating,
                    onValueChange = { rating = it },
                    valueRange = 1f..10f,
                    steps = 17
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = comment,
                    onValueChange = { comment = it },
                    label = { Text("Tu comentario") },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Escribe algo increíble...") }
                )
            }
        },
        confirmButton = {
            Button(onClick = { onSubmit(rating, comment) }, enabled = isSubmitEnabled) {
                Text("Publicar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}
