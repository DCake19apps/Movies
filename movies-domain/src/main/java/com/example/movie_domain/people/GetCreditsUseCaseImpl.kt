package com.example.movie_domain.people


class GetCreditsUseCaseImpl(
    private val repository: CreditsRepository
    ): GetCreditsUseCase {
    override suspend fun invoke(id: Int): CreditsEntity {
        return repository.getPeople(id)
    }
}