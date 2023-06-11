package com.example.movies_data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

class OkHttpClientProviderImpl() : OkHttpClientProvider {
    override fun getClient() : OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
        okHttpClientBuilder.readTimeout(10, TimeUnit.SECONDS)
        okHttpClientBuilder.connectTimeout(10, TimeUnit.SECONDS)
        okHttpClientBuilder.callTimeout(10, TimeUnit.SECONDS)

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        okHttpClientBuilder.addInterceptor(interceptor)

        return okHttpClientBuilder.build()
    }
}