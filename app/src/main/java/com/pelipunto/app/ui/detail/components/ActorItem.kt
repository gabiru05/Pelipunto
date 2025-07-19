package com.pelipunto.app.ui.detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.pelipunto.app.R
import com.pelipunto.app.movie_detail.domain.models.Cast
import com.pelipunto.app.utils.K

@Composable
fun ActorItem(
    modifier: Modifier = Modifier,
    cast: Cast
) {
    // Tu lógica para cargar la imagen es excelente y se mantiene.
    // Utiliza 'cast.profilePath' de nuestra nueva data class.
    val imgRequest = ImageRequest.Builder(LocalContext.current)
        .data("${K.BASE_IMAGE_URL}${cast.profilePath}")
        .crossfade(true)
        .build()

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = imgRequest,
            contentDescription = "Foto de ${cast.name}", // Descripción de accesibilidad mejorada
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
            onError = {
                it.result.throwable.printStackTrace()
            },
            placeholder = painterResource(id = R.drawable.baseline_person_24)
        )

        Spacer(modifier = Modifier.height(8.dp)) // Un poco más de espacio

        // ==========================================================
        // AQUÍ ESTÁN LOS CAMBIOS PRINCIPALES
        // ==========================================================

        // 1. Reemplazamos firstName y lastName por la propiedad 'name'
        Text(
            text = cast.name, // Usamos el nombre completo del actor
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            maxLines = 2, // Permitimos hasta 2 líneas para nombres largos
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )

        // 2. Reemplazamos genderRole por la propiedad 'character', que es más útil
        Text(
            text = cast.character, // Usamos el nombre del personaje
            style = MaterialTheme.typography.bodySmall,
            maxLines = 2, // Permitimos hasta 2 líneas para personajes largos
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
    }
}