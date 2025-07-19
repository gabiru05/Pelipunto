package com.pelipunto.app.movie_detail.domain.repository

import com.pelipunto.app.movie.domain.models.Movie
import com.pelipunto.app.movie_detail.domain.models.MovieDetail
import com.pelipunto.app.utils.Response
import kotlinx.coroutines.flow.Flow

interface MovieDetailRepository {
    fun fetchMovieDetail(movieId: Int): Flow<Response<MovieDetail>>
    fun fetchMovie(): Flow<Response<List<Movie>>>
}