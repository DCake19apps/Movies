package com.example.movie_domain.list

interface GetUpcomingMoviesUseCase {
    suspend fun invoke(page: Int = 1): Movies
}