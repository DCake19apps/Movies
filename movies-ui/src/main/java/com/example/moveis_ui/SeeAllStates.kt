package com.example.moveis_ui

import com.example.movie_domain.MovieEntity

sealed class SeeAllState {
    object Loading: SeeAllState()
    object Error: SeeAllState()
    data class Ready(val movies: List<MovieEntity>): SeeAllState()
}
