package com.pelipunto.app.movie_detail.data.mapper_impl

import com.pelipunto.app.common.data.ApiMapper
import com.pelipunto.app.movie_detail.data.remote.models.CastDto
import com.pelipunto.app.movie_detail.data.remote.models.MovieDetailDto
import com.pelipunto.app.movie_detail.domain.models.Cast
import com.pelipunto.app.movie_detail.domain.models.MovieDetail
import com.pelipunto.app.movie_detail.domain.models.Review
import com.pelipunto.app.utils.K // Importación necesaria
import java.text.SimpleDateFormat
import java.util.Locale

class MovieDetailMapperImpl : ApiMapper<MovieDetail, MovieDetailDto> {
    override fun mapToDomain(apiDto: MovieDetailDto): MovieDetail {
        return MovieDetail(
            backdropPath = formatEmptyValue(apiDto.backdropPath),
            genreIds = apiDto.genres?.map { formatEmptyValue(it?.name) } ?: emptyList(),
            id = apiDto.id ?: 0,
            originalLanguage = formatEmptyValue(apiDto.originalLanguage, "language"),
            originalTitle = formatEmptyValue(apiDto.originalTitle, "title"),
            overview = formatEmptyValue(apiDto.overview, "overview"),
            popularity = apiDto.popularity ?: 0.0,
            posterPath = formatEmptyValue(apiDto.posterPath),
            releaseDate = formatEmptyValue(apiDto.releaseDate, "date"),
            title = formatEmptyValue(apiDto.title, "title"),
            voteAverage = apiDto.voteAverage ?: 0.0,
            voteCount = apiDto.voteCount ?: 0,
            video = apiDto.video ?: false,
            cast = formatCast(apiDto.credits?.cast),
            language = apiDto.spokenLanguages?.map { formatEmptyValue(it?.englishName) }
                ?: emptyList(),
            productionCountry = apiDto.productionCountries?.map { formatEmptyValue(it?.name) }
                ?: emptyList(),
            reviews = apiDto.reviews?.results?.map {
                Review(
                    author = formatEmptyValue(it?.author),
                    content = formatEmptyValue(it?.content),
                    createdAt = formatTimeStamp(time = it?.createdAt ?: "0"),
                    id = formatEmptyValue(it?.id),
                    rating = it?.authorDetails?.rating ?: 0.0
                )
            } ?: emptyList(),
            runTime = convertMinutesToHours(apiDto.runtime ?: 0)
        )
    }

    private fun formatTimeStamp(pattern: String = "dd.MM.yy", time: String): String {
        val inputDateFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
        val outputDateFormatter = SimpleDateFormat(pattern, Locale.getDefault())
        val date = runCatching { inputDateFormatter.parse(time) }.getOrNull()
        return date?.let { outputDateFormatter.format(it) } ?: time
    }

    private fun convertMinutesToHours(minutes: Int): String {
        val hours = minutes / 60
        val remainingMinutes = minutes % 60
        return "${hours}h:${remainingMinutes}m"
    }

    private fun formatEmptyValue(value: String?, default: String = ""): String {
        if (value.isNullOrEmpty()) return "Unknown $default"
        return value
    }

    private fun formatCast(castDto: List<CastDto?>?): List<Cast> {
        if (castDto.isNullOrEmpty()) return emptyList()

        return castDto.mapNotNull { dto ->
            val departments = dto?.knownForDepartment?.let { listOf(it) } ?: emptyList()

            Cast(
                id = dto?.id ?: 0,
                name = formatEmptyValue(dto?.name),
                character = formatEmptyValue(dto?.character),
                // ==========================================================
                // AQUÍ ESTÁ EL ÚNICO CAMBIO: USAMOS EL NOMBRE CORRECTO
                // ==========================================================
                profilePath = dto?.profilePath?.let { "${K.BASE_IMAGE_URL}$it" } ?: "",
                department = departments
            )
        }
    }
}