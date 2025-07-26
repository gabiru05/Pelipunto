package com.pelipunto.app.di

import com.pelipunto.app.common.data.ApiMapper
import com.pelipunto.app.movie.data.mapper_impl.MovieApiMapperImpl
import com.pelipunto.app.movie.data.remote.api.MovieApiService
import com.pelipunto.app.movie.data.remote.models.MovieDto
import com.pelipunto.app.movie.data.repository_impl.MovieRepositoryImpl
import com.pelipunto.app.movie.domain.models.Movie
import com.pelipunto.app.movie.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MovieModule {

    @Provides
    @Singleton
    fun provideMovieRepository(
        movieApiService: MovieApiService,
        mapper: ApiMapper<List<Movie>, MovieDto>
    ): MovieRepository = MovieRepositoryImpl(
        movieApiService, mapper
    )

    @Provides
    @Singleton
    fun provideMovieMapper(): ApiMapper<List<Movie>, MovieDto> = MovieApiMapperImpl()
}