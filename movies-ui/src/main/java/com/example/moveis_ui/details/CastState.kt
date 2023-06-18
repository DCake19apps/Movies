package com.example.moveis_ui.details

import com.example.movie_domain.people.CastEntity

sealed class CastState {
    object Loading: CastState()
    object Error: CastState()
    data class Ready(val crew: List<CastEntity>): CastState()
}