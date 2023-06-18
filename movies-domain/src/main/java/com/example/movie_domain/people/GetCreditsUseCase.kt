package com.example.movie_domain.people

interface GetCreditsUseCase {
    suspend fun invoke(id: Int): CreditsEntity
}