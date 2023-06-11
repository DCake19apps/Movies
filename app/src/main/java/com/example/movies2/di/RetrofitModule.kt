package com.example.movies2.di

import com.example.movies_data.MoviesApi
import com.example.movies_data.OkHttpClientProvider
import com.example.movies_data.OkHttpClientProviderImpl
import com.example.movies_data.RetrofitClient
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
    fun providePlaceHolderApi(retrofitClient: RetrofitClient): MoviesApi {
        return retrofitClient.create(MoviesApi::class.java)
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