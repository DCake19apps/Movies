package com.example.movie_domain.list

class GetTopRatedMoviesUseCaseImpl(private val repo: MoviesRepository): GetTopRatedMoviesUseCase {
    override suspend fun invoke(page: Int): Movies {
        return repo.getTopRated(page)
    }
}