package com.example.movie_domain.list

class GetUpcomingMoviesUseCaseImpl(private val repo: MoviesRepository): GetUpcomingMoviesUseCase {
    override suspend fun invoke(page: Int): Movies {
        return repo.getUpcoming(page)
    }
}