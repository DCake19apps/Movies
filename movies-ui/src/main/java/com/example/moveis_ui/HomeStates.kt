package com.example.moveis_ui

import com.example.movie_domain.MovieEntity

sealed class HomeNowShowingState {
    object Loading: HomeNowShowingState()
    object Error: HomeNowShowingState()
    data class Ready(val movies: List<MovieEntity>): HomeNowShowingState()
}