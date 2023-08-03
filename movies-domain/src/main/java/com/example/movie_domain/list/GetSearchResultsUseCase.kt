package com.example.movie_domain.list

interface GetSearchResultsUseCase {
    suspend fun invoke(term: String, page: Int = 1): Movies
}