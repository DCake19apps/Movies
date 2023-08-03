package com.example.movies_data.cache

import com.example.movies_data.apipojo.MovieDetailsResult

class MoviesDetailsCacheImpl: MoviesDetailsCache {

    private val movieDetailsResults = hashMapOf<Int, MovieDetailsResult>()

    override fun save(id: Int, movieDetailsResult: MovieDetailsResult) {
        println("details_debug Cache: save $movieDetailsResult")
        movieDetailsResults[id] = movieDetailsResult
    }

    override fun get(id: Int): MovieDetailsResult? {
        println("details_debug Cache: get")
        return movieDetailsResults[id]
    }
}