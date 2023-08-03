package com.example.movies_data.cache

import com.example.movies_data.repository.list.MovieListType
import com.example.movies_data.apipojo.MoviesListResult

class MoviesCacheImpl(): MoviesCache {

    private val moviesResults = hashMapOf<MovieListType, MutableList<MoviesListResult>>()

    override fun save(type: MovieListType, moviesResult: MoviesListResult) {
        println("discover_debug Cache: save")
        println("discover_debug Cache: ${moviesResult.toString().take(20)}")
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
        println("discover_debug Cache: get")
        return moviesResults[type]?: emptyList()
    }
}