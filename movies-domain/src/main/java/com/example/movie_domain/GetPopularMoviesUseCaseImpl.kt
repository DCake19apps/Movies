package com.example.movie_domain

class GetPopularMoviesUseCaseImpl(private val repo: MoviesRepository): GetPopularMoviesUseCase {
    override suspend fun invoke(): List<MovieEntity> {
        return repo.getPopular()
    }
}