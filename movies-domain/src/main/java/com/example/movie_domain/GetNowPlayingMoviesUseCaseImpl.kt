package com.example.movie_domain

class GetNowPlayingMoviesUseCaseImpl(
    private val repo: MoviesRepository
    ): GetNowPlayingMoviesUseCase {

    override suspend fun invoke(): List<MovieEntity> {
        return repo.getNowShowing()
    }
}