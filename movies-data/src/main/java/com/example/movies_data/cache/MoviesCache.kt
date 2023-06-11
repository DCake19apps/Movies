package com.example.movies_data.cache

import com.example.movies_data.apipojo.MoviesListResult

interface MoviesCache {
    fun save(moviesResult: MoviesListResult)
    fun get(): MoviesListResult?
}