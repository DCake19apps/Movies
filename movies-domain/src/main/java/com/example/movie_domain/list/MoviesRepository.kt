package com.example.movie_domain.list

interface MoviesRepository {
    suspend fun getNowShowing(page: Int = 1): Movies
    suspend fun getUpcoming(page: Int = 1): Movies
    suspend fun getTopRated(page: Int = 1): Movies
    suspend fun getPopular(page: Int = 1): Movies
    suspend fun getDiscoverResults(filter: DiscoverFilter, page: Int = 1): Movies
    suspend fun getSearchResults(term: String, page: Int = 1): Movies
}