package com.pelipunto.app.movie.domain.repository

import com.pelipunto.app.movie.domain.models.Movie
import com.pelipunto.app.utils.Response
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun fetchDiscoverMovie(): Flow<Response<List<Movie>>>
    fun fetchTrendingMovie(): Flow<Response<List<Movie>>>
}