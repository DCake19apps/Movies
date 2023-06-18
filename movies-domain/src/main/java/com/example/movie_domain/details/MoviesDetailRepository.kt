package com.example.movie_domain.details

interface MoviesDetailRepository {
    suspend fun getMovieDetails(id: Int): MoviesDetailsEntity
}