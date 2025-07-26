package com.pelipunto.app.ui.detail

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
    // 1. Estados para controlar el diálogo y si el usuario está logueado
    var showReviewDialog by remember { mutableStateOf(false) }
    val isLoggedIn by remember { mutableStateOf(FirebaseAuth.getInstance().currentUser != null) }

    // 2. Usamos un Scaffold para poder colocar el FloatingActionButton (FAB)
    Scaffold(
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            // El botón solo aparece si los datos cargaron y el usuario está logueado
            if (state.movieDetail != null && isLoggedIn) {
                FloatingActionButton(onClick = { showReviewDialog = true }) {
                    Icon(Icons.Default.Edit, contentDescription = "Escribir reseña")
                }
            }
        },
        // Hacemos el fondo del Scaffold transparente para que no cause problemas de layout
        containerColor = Color.Transparent
    ) { innerPadding ->
        // El contenido principal de la pantalla va dentro del Box
        Box(
            modifier = Modifier
                .padding(innerPadding) // Usamos el padding que nos da el Scaffold
                .fillMaxSize()
        ) {
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

    // 3. Cuando el estado es true, mostramos el diálogo
    if (showReviewDialog) {
        ReviewInputDialog(
            onDismiss = { showReviewDialog = false },
            onSubmit = { rating, comment ->
                movieDetailViewModel.postReview(rating, comment)
                showReviewDialog = false
            }
        )
    }
}

// 4. El Composable para el diálogo de la reseña
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
                    steps = 17 // Permite pasos de 0.5
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
            Button(
                onClick = { onSubmit(rating, comment) },
                enabled = isSubmitEnabled
            ) {
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