package com.example.movies_data.api

import com.example.movies_data.apipojo.MovieDetailsResult
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDetailsApi {

    @GET("/3/movie/{id}")
    suspend fun getMovieInfo(
        @Path(value = "id", encoded = true) movieId: String,
        @Query("api_key") apiKey: String
    ): MovieDetailsResult

}