package com.example.movies_data.cache

import com.example.movies_data.repository.list.MovieListType
import com.example.movies_data.apipojo.MoviesListResult

class MoviesCacheImpl(): MoviesCache {

    private val moviesResults = hashMapOf<MovieListType, MutableList<MoviesListResult>>()

    override fun save(type: MovieListType, moviesResult: MoviesListResult) {
        println("Upcoming: cache-save")
        val results = moviesResults[type]
        if (results == null) {
            moviesResults[type] = mutableListOf(moviesResult)
        } else {
            if ((results.last().page?:0)+1 == moviesResult.page) {
                results.add(moviesResult)
                moviesResults[type] = results
            }
        }
    }

    override fun get(type: MovieListType): List<MoviesListResult> {
        println("Upcoming: cache-get: empty: ${moviesResults[type] == null}")
        return moviesResults[type]?: emptyList()
    }
}