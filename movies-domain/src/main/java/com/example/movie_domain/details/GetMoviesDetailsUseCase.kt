package com.example.movie_domain.details

interface GetMoviesDetailsUseCase {
    suspend fun invoke(id: Int): MoviesDetailsEntity
}