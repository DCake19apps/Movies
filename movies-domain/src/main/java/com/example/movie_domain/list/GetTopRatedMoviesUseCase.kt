package com.example.movie_domain.list

interface GetTopRatedMoviesUseCase {
    suspend fun invoke(page: Int = 1): Movies
}