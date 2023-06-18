package com.example.movies_data.cache

import com.example.movies_data.apipojo.CreditsResults

interface CreditsCache {
    fun save(id: Int, creditsResults: CreditsResults)
    fun get(id: Int): CreditsResults?
}