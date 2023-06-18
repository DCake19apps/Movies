package com.example.movies2.di

import com.example.movie_domain.details.GetMoviesDetailsUseCase
import com.example.movie_domain.details.GetMoviesDetailsUseCaseImpl
import com.example.movie_domain.details.MoviesDetailRepository
import com.example.movie_domain.list.GetNowPlayingMoviesUseCase
import com.example.movie_domain.list.GetNowPlayingMoviesUseCaseImpl
import com.example.movie_domain.list.GetPopularMoviesUseCase
import com.example.movie_domain.list.GetPopularMoviesUseCaseImpl
import com.example.movie_domain.list.GetTopRatedMoviesUseCase
import com.example.movie_domain.list.GetTopRatedMoviesUseCaseImpl
import com.example.movie_domain.list.GetUpcomingMoviesUseCase
import com.example.movie_domain.list.GetUpcomingMoviesUseCaseImpl
import com.example.movie_domain.list.MoviesRepository
import com.example.movie_domain.people.CreditsRepository
import com.example.movie_domain.people.GetCreditsUseCase
import com.example.movie_domain.people.GetCreditsUseCaseImpl
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
    fun provideGetNowShowingUseCase(repository: MoviesRepository): GetNowPlayingMoviesUseCase {
        return GetNowPlayingMoviesUseCaseImpl(repository)
    }

    @Singleton
    @Provides
    fun provideGetUpcomingUseCase(repository: MoviesRepository): GetUpcomingMoviesUseCase {
        return GetUpcomingMoviesUseCaseImpl(repository)
    }

    @Singleton
    @Provides
    fun provideGetTopRatedUseCase(repository: MoviesRepository): GetTopRatedMoviesUseCase {
        return GetTopRatedMoviesUseCaseImpl(repository)
    }

    @Singleton
    @Provides
    fun provideGetPopularUseCase(repository: MoviesRepository): GetPopularMoviesUseCase {
        return GetPopularMoviesUseCaseImpl(repository)
    }

    @Singleton
    @Provides
    fun provideGetMovieDetailsUseCase(repository: MoviesDetailRepository): GetMoviesDetailsUseCase {
        return GetMoviesDetailsUseCaseImpl(repository)
    }

    @Singleton
    @Provides
    fun provideGetCreditsUseCase(repository: CreditsRepository): GetCreditsUseCase {
        return GetCreditsUseCaseImpl(repository)
    }
}