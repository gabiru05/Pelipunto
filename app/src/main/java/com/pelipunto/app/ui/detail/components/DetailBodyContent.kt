package com.pelipunto.app.ui.detail.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.pelipunto.app.movie.domain.models.Movie
import com.pelipunto.app.movie_detail.domain.models.MovieDetail
import com.pelipunto.app.movie_detail.domain.models.Review
import com.pelipunto.app.ui.theme.LargePadding
import com.pelipunto.app.ui.theme.MediumPadding
import com.pelipunto.app.ui.theme.itemSpacing

@Composable
fun DetailBodyContent(
    modifier: Modifier = Modifier,
    movieDetail: MovieDetail,
    similarMovies: List<Movie>,
    onMovieClick: (Int) -> Unit,
    onActorClick: (Int) -> Unit,
    onSeeAllCastClick: () -> Unit,
    onSeeAllReviewsClick: () -> Unit,
) {
    Column(
        modifier = modifier.padding(vertical = LargePadding),
        verticalArrangement = Arrangement.spacedBy(LargePadding)
    ) {
        Column(modifier = Modifier.padding(horizontal = LargePadding)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                GenreChips(genres = movieDetail.genreIds)
                Text(text = movieDetail.runTime, style = MaterialTheme.typography.labelLarge)
            }
            Spacer(modifier = Modifier.height(itemSpacing))
            Text(text = movieDetail.title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(MediumPadding))
            Text(text = movieDetail.overview, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(LargePadding))
            ActionButtonsRow()
        }

        Divider(thickness = 1.dp, color = MaterialTheme.colorScheme.surfaceVariant)

        CastSection(
            cast = movieDetail.cast,
            onActorClick = onActorClick,
            onSeeAllClick = onSeeAllCastClick
        )

        Divider(thickness = 1.dp, color = MaterialTheme.colorScheme.surfaceVariant)

        ReviewsSection(
            reviews = movieDetail.reviews,
            onSeeAllClick = onSeeAllReviewsClick
        )
    }
}

@Composable
private fun GenreChips(genres: List<String>) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(itemSpacing)) {
        items(genres) { genre ->
            AssistChip(onClick = { /* No-op */ }, label = { Text(genre) })
        }
    }
}

@Composable
private fun ActionButtonsRow() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(LargePadding, Alignment.CenterHorizontally)
    ) {
        ActionIconBtn(icon = Icons.Default.Bookmark, label = "Guardar")
        ActionIconBtn(icon = Icons.Default.Share, label = "Compartir")
        ActionIconBtn(icon = Icons.Default.Download, label = "Descargar")
    }
}

@Composable
private fun ActionIconBtn(icon: ImageVector, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        FilledTonalIconButton(onClick = { /*TODO*/ }) {
            Icon(icon, contentDescription = label)
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = label, style = MaterialTheme.typography.labelSmall)
    }
}

@Composable
private fun SectionTitle(title: String, onSeeAllClick: (() -> Unit)? = null) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = LargePadding),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        if (onSeeAllClick != null) {
            IconButton(onClick = onSeeAllClick) {
                Icon(Icons.AutoMirrored.Filled.ArrowForwardIos, contentDescription = "Ver todo")
            }
        }
    }
}

@Composable
private fun CastSection(
    cast: List<com.pelipunto.app.movie_detail.domain.models.Cast>,
    onActorClick: (Int) -> Unit,
    onSeeAllClick: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(MediumPadding)) {
        SectionTitle(title = "Elenco Principal", onSeeAllClick = onSeeAllClick)
        LazyRow(contentPadding = PaddingValues(horizontal = LargePadding)) {
            items(cast) { actor ->
                // Aquí va tu Composable ActorItem
            }
        }
    }
}

@Composable
private fun ReviewsSection(
    reviews: List<Review>, // El tipo 'Review' ahora se resuelve
    onSeeAllClick: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(MediumPadding)) {
        SectionTitle(title = "Reseñas")
        Column(modifier = Modifier.padding(horizontal = LargePadding)) {
            if (reviews.isNotEmpty()) {
                val firstReview = reviews.first()
                Text(
                    text = "Por ${firstReview.author} • ${firstReview.createdAt.take(10)}",
                    style = MaterialTheme.typography.labelMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = firstReview.content,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 4
                )
                TextButton(onClick = onSeeAllClick) {
                    Text("Ver más reseñas")
                }
            } else {
                Text("No hay reseñas disponibles.", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}