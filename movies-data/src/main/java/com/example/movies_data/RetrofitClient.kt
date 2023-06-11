package com.example.movies_data

import okhttp3.HttpUrl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient(
    private val okHttpClientProvider: OkHttpClientProvider,
    private val baseUrl : String = "https://api.themoviedb.org/3/",
) {

    private var baseHttpUrl: HttpUrl? = null

    companion object Client{
        var retrofit: Retrofit? = null
    }

    fun setHttpUrl(baseHttpUrl: HttpUrl) {
        this.baseHttpUrl = baseHttpUrl
    }

    private fun getClient() : Retrofit {

        val r = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())

        baseHttpUrl?.let {
            r.baseUrl(it)
        }?: r.baseUrl(baseUrl)

        okHttpClientProvider.getClient()?.let {
            r.client(it)
        }

        retrofit = r.build()

        return retrofit ?: r.build()
    }

    fun <T> create(service: Class<T>): T {
        return getClient().create(service)
    }
}