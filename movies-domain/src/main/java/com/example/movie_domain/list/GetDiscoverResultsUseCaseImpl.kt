package com.example.movie_domain.list

class GetDiscoverResultsUseCaseImpl(private val repo: MoviesRepository): GetDiscoverResultsUseCase {
    override suspend fun invoke(filter: DiscoverFilter, page: Int): Movies {
        return repo.getDiscoverResults(filter, page)
    }
}