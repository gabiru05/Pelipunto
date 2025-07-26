package com.pelipunto.app.di

import com.pelipunto.app.common.data.ApiMapper
import com.pelipunto.app.movie.data.remote.models.MovieDto
import com.pelipunto.app.movie.domain.models.Movie
import com.pelipunto.app.movie_detail.data.FirestoreReviewsDataSource
import com.pelipunto.app.movie_detail.data.mapper_impl.MovieDetailMapperImpl
import com.pelipunto.app.movie_detail.data.remote.api.MovieDetailApiService
import com.pelipunto.app.movie_detail.data.remote.models.MovieDetailDto
import com.pelipunto.app.movie_detail.data.repo_impl.MovieDetailRepositoryImpl
import com.pelipunto.app.movie_detail.domain.models.MovieDetail
import com.pelipunto.app.movie_detail.domain.repository.MovieDetailRepository
import com.pelipunto.app.movie_detail.domain.use_cases.AddUserReviewUseCase
import com.pelipunto.app.movie_detail.domain.use_cases.GetMovieDetailUseCase
import com.pelipunto.app.movie_detail.domain.use_cases.GetSimilarMoviesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MovieDetailModule {

    @Provides
    @Singleton
    fun provideMovieDetailRepository(
        movieDetailApiService: MovieDetailApiService,
        firestoreDataSource: FirestoreReviewsDataSource,
        detailMapper: ApiMapper<MovieDetail, MovieDetailDto>,
        movieMapper: ApiMapper<List<Movie>, MovieDto>
    ): MovieDetailRepository = MovieDetailRepositoryImpl(
        movieDetailApiService = movieDetailApiService,
        firestoreDataSource = firestoreDataSource,
        apiDetailMapper = detailMapper,
        apiMovieMapper = movieMapper,
    )

    @Provides
    @Singleton
    fun provideMovieDetailMapper(): ApiMapper<MovieDetail, MovieDetailDto> = MovieDetailMapperImpl()


    @Provides
    @Singleton
    fun provideGetMovieDetailUseCase(repository: MovieDetailRepository): GetMovieDetailUseCase {
        return GetMovieDetailUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetSimilarMoviesUseCase(repository: MovieDetailRepository): GetSimilarMoviesUseCase {
        return GetSimilarMoviesUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideAddUserReviewUseCase(repository: MovieDetailRepository): AddUserReviewUseCase {
        return AddUserReviewUseCase(repository)
    }
}