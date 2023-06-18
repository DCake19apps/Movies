package com.example.movie_domain.list

interface GetUpcomingMoviesUseCase {
    suspend fun invoke(): List<MovieEntity>
}