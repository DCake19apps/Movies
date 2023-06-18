package com.example.movie_domain.list

class GetDiscoverResultsUseCaseImpl(private val repo: MoviesRepository): GetDiscoverResultsUseCase {
    override suspend fun invoke(filter: DiscoverFilter): List<MovieEntity> {
        return repo.getDiscoverResults(filter)
    }
}