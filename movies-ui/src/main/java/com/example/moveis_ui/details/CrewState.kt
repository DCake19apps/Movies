package com.example.moveis_ui.details

import com.example.movie_domain.people.CrewEntity

sealed class CrewState {
    object Loading: CrewState()
    object Error: CrewState()
    data class Ready(val crew: List<CrewEntity>): CrewState()
}