package com.example.movies_data.cache

import com.example.movies_data.apipojo.CreditsResults

class CreditsCacheImpl: CreditsCache {

    private val creditsResults = hashMapOf<Int, CreditsResults>()

    override fun save(id: Int, creditsResults: CreditsResults) {
        this.creditsResults[id] = creditsResults
    }

    override fun get(id: Int): CreditsResults? {
        return creditsResults[id]
    }

}