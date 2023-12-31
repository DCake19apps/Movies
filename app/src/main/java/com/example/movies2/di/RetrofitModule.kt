package com.example.movies2.di

import com.example.movies_data.api.MoviesApi
import com.example.movies_data.OkHttpClientProvider
import com.example.movies_data.OkHttpClientProviderImpl
import com.example.movies_data.RetrofitClient
import com.example.movies_data.api.CreditsApi
import com.example.movies_data.api.MovieDetailsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RetrofitModule {

    @Singleton
    @Provides
    fun provideMoviesApi(retrofitClient: RetrofitClient): MoviesApi {
        return retrofitClient.create(MoviesApi::class.java)
    }

    @Singleton
    @Provides
    fun provideMovieDetailsApi(retrofitClient: RetrofitClient): MovieDetailsApi {
        return retrofitClient.create(MovieDetailsApi::class.java)
    }

    @Singleton
    @Provides
    fun provideCreditsApi(retrofitClient: RetrofitClient): CreditsApi {
        return retrofitClient.create(CreditsApi::class.java)
    }

    @Singleton
    @Provides
    fun provideRetrofitClient(okHttpClientProvider: OkHttpClientProvider): RetrofitClient {
        return RetrofitClient(okHttpClientProvider)
    }

    @Singleton
    @Provides
    fun provideOkHttpClientProvider(): OkHttpClientProvider {
        return OkHttpClientProviderImpl()
    }

}