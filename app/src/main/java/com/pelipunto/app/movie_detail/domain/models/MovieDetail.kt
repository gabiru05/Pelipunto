package com.pelipunto.app.movie_detail.domain.models

data class MovieDetail(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
    val voteAverage: Double,
    val runTime: String,
    val genreIds: List<String>,
    val cast: List<Cast>,
    val reviews: List<Review>,
    val language: List<String>,
    val productionCountry: String
)

data class Cast(
    val id: Int,
    val name: String,
    val character: String,
    val profilePath: String
)
