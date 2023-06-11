package com.example.movies_data.cache

import com.example.movies_data.apipojo.MoviesListResult

class MoviesCacheImpl(): MoviesCache {

    private var moviesResult: MoviesListResult? = null

    override fun save(moviesResult: MoviesListResult) {
        println("Upcoming: cache-save")
        this.moviesResult = moviesResult
    }

    override fun get(): MoviesListResult? {
        println("Upcoming: cache-get: empty: ${moviesResult == null}")
        return moviesResult
    }
}