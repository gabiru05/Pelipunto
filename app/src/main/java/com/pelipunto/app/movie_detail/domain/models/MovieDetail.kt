package com.pelipunto.app.movie_detail.domain.models

data class MovieDetail(
    val backdropPath: String,
    val genreIds: List<String>,
    val id: Int,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val voteAverage: Double,
    val voteCount: Int,
    val video: Boolean,
    val cast: List<Cast>,
    val language: List<String>,
    val productionCountry: List<String>,
    val reviews: List<Review>,
    val runTime: String
)

data class Cast(
    val id: Int, // <-- ESTA ES LA LÍNEA QUE FALTABA Y QUE CORRIGE TODO
    val name: String,
    val character: String,
    val profilePath: String,
    val department: List<String>
) {
    // busca en la lista "cargo" sino cadena vacia evitando crasheo
    val director: String = department.find { it == "Directing" } ?: ""
    val writer: String = department.find { it == "Writing" } ?: ""

    // Propiedad para saber si es parte del equipo de dirección/guion
    val isCrew = director.isNotEmpty() || writer.isNotEmpty()
}

data class Review(
    val author: String,
    val content: String,
    val id: String,
    val createdAt: String,
    val rating:Double
)