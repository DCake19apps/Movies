package com.example.movie_domain

import kotlinx.coroutines.delay

class GetNowShowingMoviesUseCaseImpl(
    private val repo: MoviesRepository
    ): GetNowShowingMoviesUseCase {

    override suspend fun invoke(): List<MovieEntity> {
        return repo.getNowShowing()
    }
}