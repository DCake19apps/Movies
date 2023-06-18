package com.example.movie_domain.list

class GetSearchResultsUseCaseImpl(private val repo: MoviesRepository): GetSearchResultsUseCase {
    override suspend fun invoke(term: String): List<MovieEntity> {
        return repo.getSearchResults(term)
    }
}