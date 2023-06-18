package com.example.movie_domain.list

class GetUpcomingMoviesUseCaseImpl(private val repo: MoviesRepository): GetUpcomingMoviesUseCase {
    override suspend fun invoke(): List<MovieEntity> {
        return repo.getUpcoming()
    }
}