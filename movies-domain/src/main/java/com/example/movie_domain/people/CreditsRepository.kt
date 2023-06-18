package com.example.movie_domain.people

interface CreditsRepository {
    suspend fun getPeople(id: Int): CreditsEntity
}