package com.example.movies2.di

import com.example.movie_domain.details.MoviesDetailRepository
import com.example.movie_domain.list.MoviesRepository
import com.example.movie_domain.people.CreditsRepository
import com.example.movies_data.apikey.ApiKeyProviderImpl
import com.example.movies_data.DataRetrieverManager
import com.example.movies_data.api.CreditsApi
import com.example.movies_data.api.MovieDetailsApi
import com.example.movies_data.api.MoviesApi
import com.example.movies_data.apikey.ApiKeyProvider
import com.example.movies_data.cache.CreditsCache
import com.example.movies_data.cache.CreditsCacheImpl
import com.example.movies_data.repository.list.MoviesMapperImpl
import com.example.movies_data.repository.list.MoviesRepositoryImpl
import com.example.movies_data.cache.MoviesCache
import com.example.movies_data.cache.MoviesCacheImpl
import com.example.movies_data.cache.MoviesDetailsCache
import com.example.movies_data.cache.MoviesDetailsCacheImpl
import com.example.movies_data.repository.detail.MoviesDetailRepositoryImpl
import com.example.movies_data.repository.detail.MoviesDetailsMapperImpl
import com.example.movies_data.repository.people.CreditsMapperImpl
import com.example.movies_data.repository.people.CreditsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DataModule {

    @Singleton
    @Provides
    fun provideMoviesRepository(
        apiKeyProvider: ApiKeyProvider,
        moviesApi: MoviesApi,
        cache: MoviesCache
    ): MoviesRepository {
        return MoviesRepositoryImpl(
            apiKeyProvider,
            moviesApi,
            cache,
            MoviesMapperImpl(),
            DataRetrieverManager(),
            DataRetrieverManager(),
            DataRetrieverManager(),
            DataRetrieverManager(),
            DataRetrieverManager(),
            DataRetrieverManager()
        )
    }

    @Singleton
    @Provides
    fun provideDetailsRepository(
        apiKeyProvider: ApiKeyProvider,
        movieDetailsApi: MovieDetailsApi,
        cache: MoviesDetailsCache,
    ): MoviesDetailRepository {
        return MoviesDetailRepositoryImpl(
            apiKeyProvider,
            movieDetailsApi,
            cache,
            MoviesDetailsMapperImpl(),
            DataRetrieverManager()
        )
    }

    @Singleton
    @Provides
    fun provideCreditsRepository(
        apiKeyProvider: ApiKeyProvider,
        creditsApi: CreditsApi,
        cache: CreditsCache,
    ): CreditsRepository {
        return CreditsRepositoryImpl(
            apiKeyProvider,
            creditsApi,
            cache,
            CreditsMapperImpl(),
            DataRetrieverManager()
        )
    }

    @Singleton
    @Provides
    fun provideMoviesCache(): MoviesCache {
        return MoviesCacheImpl()
    }

    @Singleton
    @Provides
    fun provideDetailsCache(): MoviesDetailsCache {
        return MoviesDetailsCacheImpl()
    }

    @Singleton
    @Provides
    fun provideCreditsCache(): CreditsCache {
        return CreditsCacheImpl()
    }

    @Singleton
    @Provides
    fun provideApiKeyProvider(): ApiKeyProvider {
        return ApiKeyProviderImpl()
    }
}