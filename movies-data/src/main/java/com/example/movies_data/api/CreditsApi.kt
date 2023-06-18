package com.example.movies_data.api

import com.example.movies_data.apipojo.CreditsResults
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CreditsApi {
    @GET("/3/movie/{id}/credits")
    suspend fun getCredits(
        @Path(value = "id", encoded = true) movieId: String,
        @Query("api_key") apiKey: String
    ): CreditsResults
}