package com.example.movie_domain.list

class GetPopularMoviesUseCaseImpl(private val repo: MoviesRepository): GetPopularMoviesUseCase {
    override suspend fun invoke(page: Int): Movies {
        return repo.getPopular(page)
    }
}