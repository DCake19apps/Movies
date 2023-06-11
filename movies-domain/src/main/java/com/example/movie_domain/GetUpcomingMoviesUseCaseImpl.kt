package com.example.movie_domain

class GetUpcomingMoviesUseCaseImpl(private val repo: MoviesRepository): GetUpcomingMoviesUseCase {
    override suspend fun invoke(): List<MovieEntity> {
        return repo.getUpcoming()
    }
}