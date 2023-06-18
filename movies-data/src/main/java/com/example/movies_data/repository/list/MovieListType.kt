package com.example.movies_data.repository.list

import com.example.movie_domain.list.DiscoverFilter

//enum class MovieListType {
//    NOW_PLAYING, UPCOMING, POPULAR, TOP_RATED
//}

sealed class MovieListType {
    object NowPlaying: MovieListType()
    object Upcoming: MovieListType()
    object Popular: MovieListType()
    object TopRated: MovieListType()
    data class DiscoverResults(val filter: DiscoverFilter): MovieListType()
    data class SearchResults(val term: String): MovieListType()
}