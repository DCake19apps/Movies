package com.example.movie_domain

interface GetPopularMoviesUseCase {
    suspend fun invoke(): List<MovieEntity>
}