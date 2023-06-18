package com.example.movie_domain.list

data class DiscoverFilter(
    val sortBy: SortBy,
    val minVoteAverage: Float,
    val genres: List<Int>,
    val releaseYear: Int?
    )

enum class SortBy {
    VOTE_AVERAGE, POPULARITY
}
