package com.pelipunto.app.movie_detail.data.repo_impl

import com.pelipunto.app.common.data.ApiMapper
import com.pelipunto.app.movie.data.remote.models.MovieDto
import com.pelipunto.app.movie.domain.models.Movie
import com.pelipunto.app.movie_detail.data.FirestoreReviewsDataSource
import com.pelipunto.app.movie_detail.data.remote.api.MovieDetailApiService
import com.pelipunto.app.movie_detail.data.remote.models.MovieDetailDto
import com.pelipunto.app.movie_detail.domain.models.MovieDetail
import com.pelipunto.app.movie_detail.domain.models.Review
import com.pelipunto.app.movie_detail.domain.models.UserReview
import com.pelipunto.app.movie_detail.domain.repository.MovieDetailRepository
import com.pelipunto.app.utils.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieDetailRepositoryImpl @Inject constructor(
    private val movieDetailApiService: MovieDetailApiService,
    private val firestoreDataSource: FirestoreReviewsDataSource,
    private val apiDetailMapper: ApiMapper<MovieDetail, MovieDetailDto>,
    private val apiMovieMapper: ApiMapper<List<Movie>, MovieDto>,
) : MovieDetailRepository {

    override fun fetchMovieDetail(movieId: Int): Flow<Response<MovieDetail>> = flow {
        emit(Response.Loading())
        val movieDetailDto = movieDetailApiService.fetchMovieDetail(movieId)
        val userReviews: List<Review> = firestoreDataSource.getReviewsForMovie(movieId).map { it.toDomainModel() }
        val movieDetailFromApi = apiDetailMapper.mapToDomain(movieDetailDto)
        val combinedReviews = (movieDetailFromApi.reviews + userReviews).sortedByDescending { it.createdAt }
        val finalMovieDetail = movieDetailFromApi.copy(reviews = combinedReviews)
        emit(Response.Success(finalMovieDetail))
    }.catch { e ->
        e.printStackTrace()
        emit(Response.Error(e))
    }

    override fun fetchSimilarMovies(movieId: Int): Flow<Response<List<Movie>>> = flow {
        emit(Response.Loading())
        val movieDto = movieDetailApiService.fetchSimilarMovies(movieId)
        apiMovieMapper.mapToDomain(movieDto).apply {
            emit(Response.Success(this))
        }
    }.catch { e ->
        e.printStackTrace()
        emit(Response.Error(e))
    }

    override fun addReview(review: UserReview): Flow<Response<Unit>> = flow {
        emit(Response.Loading())
        val result = firestoreDataSource.addReview(review)
        if (result.isSuccess) {
            emit(Response.Success(Unit))
        } else {
            emit(Response.Error(result.exceptionOrNull() ?: Exception("Error al añadir la reseña")))
        }
    }.catch { e ->
        e.printStackTrace()
        emit(Response.Error(e))
    }


    override fun getMovieTrailer(movieId: Int): Flow<Response<String>> = flow<Response<String>> {
        val response = movieDetailApiService.getMovieVideos(movieId)
        val trailerKey = response.results
            ?.firstOrNull { it.site == "YouTube" && it.type == "Trailer" && it.official == true }
            ?.key
            ?: response.results?.firstOrNull { it.site == "YouTube" && it.type == "Trailer" }?.key

        if (trailerKey != null) {
            emit(Response.Success(trailerKey))
        } else {
            emit(Response.Error(Exception("Tráiler no encontrado")))
        }
    }.catch { e ->
        e.printStackTrace()
        emit(Response.Error(e))
    }
}