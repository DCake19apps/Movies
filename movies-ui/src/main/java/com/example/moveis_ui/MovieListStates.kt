package com.example.moveis_ui

import com.example.movie_domain.list.MovieEntity

sealed class MovieListState {
    object Loading: MovieListState()
    object Error: MovieListState()
    data class Ready(
        val movies: List<MovieEntity>,
        val complete: Boolean,
        val lastPage: Int
        ): MovieListState()
}
