package com.example.movie_domain

class GetTopRatedMoviesUseCaseImpl(private val repo: MoviesRepository): GetTopRatedMoviesUseCase {
    override suspend fun invoke(): List<MovieEntity> {
        return repo.getTopRated()
    }
}