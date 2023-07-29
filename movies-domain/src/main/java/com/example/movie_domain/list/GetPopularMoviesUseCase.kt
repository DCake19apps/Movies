package com.example.movie_domain.list

interface GetPopularMoviesUseCase {
    suspend fun invoke(page: Int = 1): Movies
}