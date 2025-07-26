package com.pelipunto.app.movie_detail.data.mapper_impl

import com.pelipunto.app.common.data.ApiMapper
import com.pelipunto.app.movie_detail.data.remote.models.MovieDetailDto
import com.pelipunto.app.movie_detail.domain.models.Cast
import com.pelipunto.app.movie_detail.domain.models.MovieDetail
import com.pelipunto.app.movie_detail.domain.models.Review
import javax.inject.Inject

class MovieDetailMapperImpl @Inject constructor() : ApiMapper<MovieDetail, MovieDetailDto> {
    override fun mapToDomain(dto: MovieDetailDto): MovieDetail {
        return MovieDetail(
            id = dto.id ?: 0,
            title = dto.title ?: "N/A",
            overview = dto.overview ?: "",
            posterPath = dto.posterPath ?: "",
            releaseDate = dto.releaseDate ?: "",
            voteAverage = dto.voteAverage ?: 0.0,
            runTime = "${dto.runtime ?: 0} min",
            genreIds = dto.genres?.mapNotNull { it?.name } ?: emptyList(),
            cast = dto.credits?.cast?.mapNotNull { castDto ->
                // Verificamos que los datos esenciales no sean nulos antes de crear el objeto Cast
                if (castDto?.id != null && castDto.name != null) {
                    Cast(
                        id = castDto.id,
                        name = castDto.name,
                        character = castDto.character ?: "",
                        profilePath = castDto.profilePath ?: ""
                    )
                } else {
                    null
                }
            } ?: emptyList(),
            reviews = dto.reviews?.results?.mapNotNull { reviewDto ->
                // Verificamos que los datos esenciales no sean nulos
                if (reviewDto?.author != null && reviewDto.content != null) {
                    Review(
                        author = reviewDto.author,
                        content = reviewDto.content,
                        createdAt = reviewDto.createdAt ?: "",
                        rating = reviewDto.authorDetails?.rating ?: 0.0,
                        isOfficial = true,
                        userPhotoUrl = null
                    )
                } else {
                    null
                }
            } ?: emptyList(),
            language = dto.spokenLanguages?.mapNotNull { it?.englishName } ?: emptyList(),
            productionCountry = dto.productionCountries?.firstOrNull()?.name ?: "N/A"
        )
    }
}