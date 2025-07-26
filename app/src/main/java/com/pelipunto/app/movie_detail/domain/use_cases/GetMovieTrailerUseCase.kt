package com.pelipunto.app.movie_detail.domain.use_cases

import com.pelipunto.app.movie_detail.domain.repository.MovieDetailRepository
import com.pelipunto.app.utils.Response
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieTrailerUseCase @Inject constructor(
    private val repository: MovieDetailRepository
) {
    operator fun invoke(movieId: Int): Flow<Response<String>> {
        return repository.getMovieTrailer(movieId)
    }
}