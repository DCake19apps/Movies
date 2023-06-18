package com.example.movies_data.cache

import com.example.movies_data.apipojo.MovieDetailsResult

class MoviesDetailsCacheImpl: MoviesDetailsCache {

    private val movieDetailsResults = hashMapOf<Int, MovieDetailsResult>()

    override fun save(id: Int, movieDetailsResult: MovieDetailsResult) {
        movieDetailsResults[id] = movieDetailsResult
    }

    override fun get(id: Int): MovieDetailsResult? {
        return movieDetailsResults[id]
    }
}