package com.example.movie_domain.list

class GetNowPlayingMoviesUseCaseImpl(
    private val repo: MoviesRepository
    ): GetNowPlayingMoviesUseCase {

    override suspend fun invoke(page: Int): Movies {
        return repo.getNowShowing(page)
    }
}