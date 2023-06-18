package com.example.movies_data.cache

import com.example.movies_data.repository.list.MovieListType
import com.example.movies_data.apipojo.MoviesListResult

class MoviesCacheImpl(): MoviesCache {

    private val moviesResults = hashMapOf<MovieListType, MoviesListResult>()

    override fun save(type: MovieListType, moviesResult: MoviesListResult) {
        println("Upcoming: cache-save")
        moviesResults[type] = moviesResult
    }

    override fun get(type: MovieListType): MoviesListResult? {
        println("Upcoming: cache-get: empty: ${moviesResults[type] == null}")
        return moviesResults[type]
    }
}