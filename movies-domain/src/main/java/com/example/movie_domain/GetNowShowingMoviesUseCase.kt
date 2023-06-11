package com.example.movie_domain

interface GetNowShowingMoviesUseCase {
    suspend fun invoke(): List<MovieEntity>
}