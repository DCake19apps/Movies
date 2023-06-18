package com.example.movie_domain.list

interface GetPopularMoviesUseCase {
    suspend fun invoke(): List<MovieEntity>
}