package com.example.moveis_ui.details

import com.example.movie_domain.details.MoviesDetailsEntity

sealed class    DetailsState {
    object Loading: DetailsState()
    object Error: DetailsState()
    data class Ready(val details: MoviesDetailsEntity): DetailsState()
}
