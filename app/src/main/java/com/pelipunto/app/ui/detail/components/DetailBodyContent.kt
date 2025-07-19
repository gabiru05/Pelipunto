package com.pelipunto.app.ui.detail.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.pelipunto.app.movie.domain.models.Movie
import com.pelipunto.app.movie_detail.domain.models.MovieDetail
import com.pelipunto.app.movie_detail.domain.models.Review
import com.pelipunto.app.ui.home.components.MovieCard
import com.pelipunto.app.ui.home.components.MovieCoverImage
import com.pelipunto.app.ui.home.defaultPadding
import com.pelipunto.app.ui.home.itemSpacing

@Composable
fun DetailBodyContent(
    modifier: Modifier = Modifier,
    movieDetail: MovieDetail,
    movies: List<Movie>,
    isMovieLoading: Boolean,
    fetchMovies: () -> Unit,
    onMovieClick: (Int) -> Unit,
    onActorClick: (Int) -> Unit,
) {
    LazyColumn(modifier) {
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(defaultPadding)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Start, // Alineado al inicio
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            movieDetail.genreIds.forEachIndexed { index, genreText ->
                                Text(
                                    text = genreText,
                                    style = MaterialTheme.typography.bodySmall
                                )
                                // Muestra el separador solo si no es el último género
                                if (index < movieDetail.genreIds.lastIndex) {
                                    Text(
                                        text = " • ",
                                        style = MaterialTheme.typography.bodySmall,
                                        modifier = Modifier.padding(horizontal = 4.dp)
                                    )
                                }
                            }
                        }
                        Text(
                            text = movieDetail.runTime,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    Spacer(modifier = Modifier.height(itemSpacing))
                    Text(
                        text = movieDetail.title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(modifier = Modifier.height(itemSpacing))
                    Text(
                        text = movieDetail.overview,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Spacer(modifier = Modifier.height(itemSpacing))
                    Row(modifier = Modifier.fillMaxWidth()) {
                        ActionIcon.entries.forEachIndexed { index, actionIcon ->
                            ActionIconBtn(
                                icon = actionIcon.icon,
                                contentDescription = actionIcon.contentDescription,
                                bgColor = if (index == ActionIcon.entries.lastIndex)
                                    MaterialTheme.colorScheme.primaryContainer
                                else Color.Black.copy(
                                    .5f
                                )
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(itemSpacing))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = itemSpacing),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Elenco Principal",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                        )
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                                contentDescription = "Ver todo el elenco"
                            )
                        }
                    }

                    // ==========================================================
                    // CORRECCIÓN PRINCIPAL APLICADA AQUÍ
                    // ==========================================================
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(defaultPadding)
                    ) {
                        items(
                            items = movieDetail.cast,
                            key = { actor -> actor.id } // 1. Ahora 'actor.id' existe y se usa como clave.
                        ) { actor ->
                            ActorItem(
                                cast = actor,
                                // 2. Se quita el 'weight(1f)' y se mantiene el 'clickable'.
                                //    La acción de click ahora usa 'actor.id', que es válido.
                                modifier = Modifier.clickable { onActorClick(actor.id) }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(itemSpacing))

                    MovieInfoItem(
                        infoItem = movieDetail.language,
                        title = "Idioma Original:",
                    )
                    Spacer(modifier = Modifier.height(itemSpacing))
                    MovieInfoItem(
                        infoItem = movieDetail.productionCountry,
                        title = "Países de Producción:",
                    )
                    Spacer(modifier = Modifier.height(itemSpacing))
                    Text(
                        text = "Reseñas",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(itemSpacing))
                    Review(reviews = movieDetail.reviews)
                    Spacer(modifier = Modifier.height(itemSpacing))
                    MoreLikeThis(
                        fetchMovies = fetchMovies,
                        isMovieLoading = isMovieLoading,
                        movies = movies,
                        onMovieClick = onMovieClick
                    )

                }
            }
        }
    }
}


@Composable
fun MoreLikeThis(
    modifier: Modifier = Modifier,
    fetchMovies: () -> Unit,
    isMovieLoading: Boolean,
    movies: List<Movie>,
    onMovieClick: (Int) -> Unit,
) {
    LaunchedEffect(key1 = true) {
        fetchMovies()
    }
    Column(modifier) {
        Text(
            text = "Más como esto",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            item {
                AnimatedVisibility(visible = isMovieLoading) {
                    CircularProgressIndicator()
                }
            }
            items(movies) {
                MovieCoverImage(movie = it, onMovieClick = onMovieClick)
            }
        }
    }
}

private enum class ActionIcon(val icon: ImageVector, val contentDescription: String) {
    BookMark(icon = Icons.Default.Bookmark, "bookmark"),
    Share(icon = Icons.Default.Share, "Share"),
    Download(icon = Icons.Default.Download, "Download"),
}

@Composable
private fun ActionIconBtn(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    contentDescription: String? = null,
    bgColor: Color = Color.Black.copy(.8f)
) {
    MovieCard(
        shapes = CircleShape,
        modifier = modifier
            .padding(4.dp),
        bgColor = bgColor
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            modifier = Modifier.padding(4.dp)
        )
    }
}

@Composable
private fun MovieInfoItem(infoItem: List<String>, title: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        // Eliminado Arrangement.Center para un mejor alineamiento a la izquierda
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.width(8.dp)) // Más espacio
        infoItem.forEach {
            Text(
                text = it,
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}

@Composable
private fun Review(
    modifier: Modifier = Modifier,
    reviews: List<Review>
) {
    val (viewMore, setViewMore) = remember {
        mutableStateOf(false)
    }

    // Si no hay reseñas, no mostramos nada
    if (reviews.isEmpty()) {
        Text(text = "No hay reseñas disponibles.", style = MaterialTheme.typography.bodyMedium)
        return
    }

    val defaultReview = if (reviews.size > 3) reviews.take(3) else reviews
    val movieReviews = if (viewMore) reviews else defaultReview
    val btnText = if (viewMore) "Contraer" else "Más..."

    Column(modifier) {
        movieReviews.forEach { review ->
            // Asumo que tienes un componente ReviewItem, si no, esto fallará
            // ReviewItem(review = review)
            Text(text = "Autor: ${review.author}\n${review.content}") // Placeholder si ReviewItem no existe
            Spacer(modifier = Modifier.height(itemSpacing))
            HorizontalDivider(modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(itemSpacing))
        }

        // Solo mostramos el botón "Más..." si hay más de 3 reseñas
        if (reviews.size > 3) {
            TextButton(onClick = { setViewMore(!viewMore) }) {
                Text(text = btnText)
            }
        }
    }
}