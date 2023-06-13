package com.example.moveis_ui

import com.example.movie_domain.MovieEntity

sealed class HomeState {
    object Loading: HomeState()
    object Error: HomeState()
    data class Ready(val movies: List<MovieEntity>): HomeState()
}
