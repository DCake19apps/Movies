package com.example.movies2.di

import com.example.movie_domain.GetNowShowingMoviesUseCase
import com.example.movie_domain.GetNowShowingMoviesUseCaseImpl
import com.example.movie_domain.GetUpcomingMoviesUseCase
import com.example.movie_domain.GetUpcomingMoviesUseCaseImpl
import com.example.movie_domain.MoviesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class UseCaseModule {

    @Singleton
    @Provides
    fun provideGetNowShowingUseCase(repository: MoviesRepository): GetNowShowingMoviesUseCase {
        return GetNowShowingMoviesUseCaseImpl(repository)
    }

    @Singleton
    @Provides
    fun provideGetUpcomingUseCase(repository: MoviesRepository): GetUpcomingMoviesUseCase {
        return GetUpcomingMoviesUseCaseImpl(repository)
    }
}