package com.example.movie_domain.people

data class CreditsEntity(val cast: List<CastEntity>, val crew: List<CrewEntity>)
data class CastEntity(val id: Int, val name: String, val character: String, val profilePath: String)
data class CrewEntity(val id: Int, val name: String, val job: String, val profilePath: String)
