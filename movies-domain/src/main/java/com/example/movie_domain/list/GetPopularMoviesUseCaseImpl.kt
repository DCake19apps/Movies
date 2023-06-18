package com.example.movie_domain.list

class GetPopularMoviesUseCaseImpl(private val repo: MoviesRepository): GetPopularMoviesUseCase {
    override suspend fun invoke(): List<MovieEntity> {
        return repo.getPopular()
    }
}