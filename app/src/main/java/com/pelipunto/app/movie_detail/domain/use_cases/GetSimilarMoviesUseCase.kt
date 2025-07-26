package com.pelipunto.app.movie_detail.domain.use_cases

import com.pelipunto.app.movie_detail.domain.repository.MovieDetailRepository
import javax.inject.Inject

class GetSimilarMoviesUseCase @Inject constructor(
    private val repository: MovieDetailRepository
) {
    operator fun invoke(movieId: Int) = repository.fetchSimilarMovies(movieId)
}