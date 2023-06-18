package com.example.movie_domain.list

interface MoviesRepository {
    suspend fun getNowShowing(): List<MovieEntity>
    suspend fun getUpcoming(): List<MovieEntity>
    suspend fun getTopRated(): List<MovieEntity>
    suspend fun getPopular(): List<MovieEntity>
}