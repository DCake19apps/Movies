package com.example.movie_domain

interface GetTopRatedMoviesUseCase {
    suspend fun invoke(): List<MovieEntity>
}