package com.example.movies_data.api

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

    @GET("/3/movie/top_rated")
    suspend fun getTopRated(
        @Query("api_key") apiKey: String,
        @Query("page") pageNumber: Int
    ): MoviesListResult

    @GET("/3/movie/popular")
    suspend fun getPopular(
        @Query("api_key") apiKey: String,
        @Query("page") pageNumber: Int
    ): MoviesListResult

    // sort_by from { popularity.desc, vote_average.desc}
    // with_genres comma separated ids
    @GET("/3/discover/movie")
    suspend fun getDiscoverResults(
        @Query("api_key") apiKey: String,
        @Query("sort_by") sortBy: String,
        @Query("vote_average.gte") minVoteAverage: String,
        @Query("vote_count.gte") minVoteCount: String,
        @Query("with_genres") withGenres: String,
        @Query("primary_release_year") year: String,
        @Query("page") pageNumber: Int
    ): MoviesListResult

    @GET("/3/search/movie")
    suspend fun getSearchResults(
        @Query("api_key") apiKey: String,
        @Query("query") query: String,
        @Query("page") pageNumber: Int
    ): MoviesListResult
}