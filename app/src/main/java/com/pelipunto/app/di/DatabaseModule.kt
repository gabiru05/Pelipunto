package com.pelipunto.app.di

import com.pelipunto.app.movie_detail.data.FirestoreReviewsDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideFirestoreReviewsDataSource(): FirestoreReviewsDataSource {
        return FirestoreReviewsDataSource()
    }
}