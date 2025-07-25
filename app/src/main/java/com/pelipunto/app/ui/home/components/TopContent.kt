package com.pelipunto.app.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.pelipunto.app.R
import com.pelipunto.app.movie.domain.models.Movie
import com.pelipunto.app.ui.theme.defaultPadding
import com.pelipunto.app.ui.theme.itemSpacing
import com.pelipunto.app.utils.K

@Composable
fun TopContent(
    modifier: Modifier = Modifier,
    movie: Movie,
    onMovieClick: (id: Int) -> Unit
) {
    val imgRequest = ImageRequest.Builder(LocalContext.current)
        .data("${K.BASE_IMAGE_URL}${movie.posterPath}")
        .crossfade(true)
        .build()

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp)
            .clickable { onMovieClick(movie.id) },
        shape = MaterialTheme.shapes.large
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = imgRequest,
                contentDescription = null,
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.Crop,
                onError = { it.result.throwable.printStackTrace() },
                placeholder = painterResource(id = R.drawable.bg_image_movie)
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black),
                            startY = 400f
                        )
                    )
            )
            MovieDetail(
                rating = movie.voteAverage,
                title = movie.title,
                genre = movie.genreIds,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
fun MovieDetail(
    modifier: Modifier = Modifier,
    rating: Double,
    title: String,
    genre: List<String>,
) {
    Column(
        modifier = modifier.padding(defaultPadding),
        verticalArrangement = Arrangement.spacedBy(itemSpacing)
    ) {
        MovieCard(shape = MaterialTheme.shapes.medium) {
            Row(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = "Rating",
                    tint = Color.Yellow
                )
                Text(text = String.format("%.1f", rating), style = MaterialTheme.typography.labelMedium)
            }
        }
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = Color.White
        )
        if (genre.isNotEmpty()) {
            MovieCard(shape = MaterialTheme.shapes.medium) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    genre.take(3).forEachIndexed { index, genreText ->
                        Text(
                            text = genreText,
                            style = MaterialTheme.typography.labelSmall,
                            maxLines = 1
                        )
                        if (index < genre.take(3).lastIndex) {
                            Text(" • ", color = Color.White, style = MaterialTheme.typography.labelSmall)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PrevMovieDetail() {
    MovieDetail(
        rating = 7.5,
        title = "Doctor Strange in the Multiverse of Madness",
        genre = listOf("Action", "Adventure", "Fantasy")
    )
}