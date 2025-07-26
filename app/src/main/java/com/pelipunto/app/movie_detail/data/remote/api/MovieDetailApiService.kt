package com.pelipunto.app.movie_detail.data.remote.api

import com.pelipunto.app.BuildConfig
import com.pelipunto.app.movie.data.remote.models.MovieDto
import com.pelipunto.app.movie_detail.data.remote.models.MovieDetailDto
import com.pelipunto.app.movie_detail.data.remote.models.VideoResponseDto // <-- Importación añadida
import com.pelipunto.app.utils.K
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val MOVIE_ID = "movie_id"

interface MovieDetailApiService {

    @GET("${K.MOVIE_DETAIL_ENDPOINT}/{$MOVIE_ID}")
    suspend fun fetchMovieDetail(
        @Path(MOVIE_ID) movieId: Int,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query("append_to_response") appendToResponse: String = "credits,reviews"
    ): MovieDetailDto

    @GET("${K.MOVIE_DETAIL_ENDPOINT}/{$MOVIE_ID}/similar")
    suspend fun fetchSimilarMovies(
        @Path(MOVIE_ID) movieId: Int,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY
    ): MovieDto

    @GET("movie/{$MOVIE_ID}/videos")
    suspend fun getMovieVideos(
        @Path(MOVIE_ID) movieId: Int,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY
    ): VideoResponseDto
}