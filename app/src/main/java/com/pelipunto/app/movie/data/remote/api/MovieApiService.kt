// En MovieApiService.kt

package com.pelipunto.app.movie.data.remote.api

// El import correcto puede ser este o uno similar, Android Studio te lo sugerirá
import com.pelipunto.app.BuildConfig
import com.pelipunto.app.movie.data.remote.models.MovieDto
import com.pelipunto.app.utils.K
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {

    @GET(K.MOVIE_ENDPOINT)
    suspend fun fetchDiscoverMovie(
        // Asegúrate de que usa el nombre correcto: TMDB_API_KEY
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query("include_adult") includeAdult: Boolean = false
    ): MovieDto

    @GET(K.TRENDING_MOVIE_ENDPOINT)
    suspend fun fetchTrendingMovie(
        // Y aquí también
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query("include_adult") includeAdult: Boolean = false
    ): MovieDto
}