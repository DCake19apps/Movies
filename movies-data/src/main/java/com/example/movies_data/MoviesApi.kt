package com.example.movies_data

import com.example.movies_data.apipojo.MoviesListResult
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {
    @GET("/3/movie/now_playing")
    suspend fun getNowPlaying(
        @Query("api_key") apiKey: String,
        @Query("page") pageNumber: Int
    ): MoviesListResult

    @GET("/3/movie/upcoming")
    suspend fun getUpcoming(
        @Query("api_key") apiKey: String,
        @Query("page") pageNumber: Int
    ): MoviesListResult
}