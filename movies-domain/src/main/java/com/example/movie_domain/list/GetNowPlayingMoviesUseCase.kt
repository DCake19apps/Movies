package com.example.movie_domain.list

interface GetNowPlayingMoviesUseCase {
    suspend fun invoke(page: Int = 1): Movies
}