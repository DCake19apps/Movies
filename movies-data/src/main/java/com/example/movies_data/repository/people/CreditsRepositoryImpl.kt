package com.example.movies_data.repository.people

import com.example.movie_domain.people.CreditsEntity
import com.example.movie_domain.people.CreditsRepository
import com.example.movies_data.DataRetrieverManager
import com.example.movies_data.api.CreditsApi
import com.example.movies_data.apikey.ApiKeyProvider
import com.example.movies_data.cache.CreditsCache

class CreditsRepositoryImpl(
    private val apiKeyProvider: ApiKeyProvider,
    private val api: CreditsApi,
    private val cache: CreditsCache,
    private val creditsMapper: CreditsMapper,
    private val creditsDataRetrieverManager: DataRetrieverManager<CreditsEntity>
): CreditsRepository {

    override suspend fun getPeople(id: Int): CreditsEntity {
        return creditsDataRetrieverManager.get { retrieve(id) }
    }

    private suspend fun retrieve(id: Int): CreditsEntity {
        val cached = cache.get(id)

        val credits = if (cached != null) {
            cached
        } else {
            val apiCredits = api.getCredits(id.toString(), apiKeyProvider.getApiKey())
            cache.save(id, apiCredits)
            apiCredits
        }

        return creditsMapper.map(credits)
    }
}