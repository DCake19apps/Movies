package com.example.movie_domain.list

interface GetDiscoverResultsUseCase {
    suspend fun invoke(filter: DiscoverFilter, page: Int = 1): Movies
}