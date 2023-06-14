package com.example.movie_domain

interface GetNowPlayingMoviesUseCase {
    suspend fun invoke(): List<MovieEntity>
}