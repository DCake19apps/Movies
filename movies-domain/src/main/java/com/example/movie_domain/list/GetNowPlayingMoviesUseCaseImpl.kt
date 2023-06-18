package com.example.movie_domain.list

class GetNowPlayingMoviesUseCaseImpl(
    private val repo: MoviesRepository
    ): GetNowPlayingMoviesUseCase {

    override suspend fun invoke(): List<MovieEntity> {
        return repo.getNowShowing()
    }
}