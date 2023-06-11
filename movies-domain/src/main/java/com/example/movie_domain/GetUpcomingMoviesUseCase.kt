package com.example.movie_domain

interface GetUpcomingMoviesUseCase {
    suspend fun invoke(): List<MovieEntity>
}