package com.example.movie_domain.list

interface GetTopRatedMoviesUseCase {
    suspend fun invoke(): List<MovieEntity>
}