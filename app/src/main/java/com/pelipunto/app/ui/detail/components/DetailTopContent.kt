package com.pelipunto.app.ui.detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.pelipunto.app.R
import com.pelipunto.app.movie_detail.domain.models.MovieDetail
import com.pelipunto.app.ui.home.components.MovieCard
import com.pelipunto.app.ui.theme.LargePadding
import com.pelipunto.app.ui.theme.MediumPadding

@Composable
fun DetailTopContent(
    modifier: Modifier = Modifier,
    movieDetail: MovieDetail,
    onNavigateUp: () -> Unit
) {
    val imgRequest = ImageRequest.Builder(LocalContext.current)
        .data("https://image.tmdb.org/t/p/w780${movieDetail.posterPath}")
        .crossfade(true)
        .build()

    Box(modifier = modifier.height(400.dp)) { // Damos una altura más controlada
        // Imagen de fondo
        AsyncImage(
            model = imgRequest,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.bg_image_movie)
        )

        // Gradiente para asegurar legibilidad del texto
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.8f)),
                        startY = 600f
                    )
                )
        )

        // Contenido superpuesto
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(MediumPadding),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Botón para volver atrás
            IconButton(onClick = onNavigateUp) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Volver",
                    tint = Color.White
                )
            }

            // Información y botones de acción en la parte inferior
            Column(verticalArrangement = Arrangement.spacedBy(MediumPadding)) {
                // Rating y Fecha
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(MediumPadding)
                ) {
                    MovieCard(shape = MaterialTheme.shapes.medium) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(imageVector = Icons.Filled.Star, contentDescription = "Rating", tint = Color.Yellow)
                            Text(text = String.format("%.1f", movieDetail.voteAverage), style = MaterialTheme.typography.labelMedium)
                        }
                    }
                    Text(text = movieDetail.releaseDate, style = MaterialTheme.typography.bodyMedium, color = Color.White)
                }

                // Botones de acción
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(MediumPadding)
                ) {
                    Button(
                        onClick = { /*TODO*/ },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Default.PlayArrow, contentDescription = null)
                        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                        Text("Ver Ahora")
                    }
                    FilledTonalButton(
                        onClick = { /*TODO*/ },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Default.Movie, contentDescription = null)
                        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                        Text("Ver Tráiler")
                    }
                }
            }
        }
    }
}