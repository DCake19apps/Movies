package com.example.movies_data

import okhttp3.OkHttpClient

interface OkHttpClientProvider {
    fun getClient() : OkHttpClient?
}