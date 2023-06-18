package com.example.movie_domain.list

interface GetNowPlayingMoviesUseCase {
    suspend fun invoke(): List<MovieEntity>
}