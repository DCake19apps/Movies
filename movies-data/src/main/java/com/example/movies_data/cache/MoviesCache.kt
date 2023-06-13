package com.example.movies_data.cache

import com.example.movies_data.MovieListType
import com.example.movies_data.apipojo.MoviesListResult

interface MoviesCache {
    fun save(type: MovieListType, moviesResult: MoviesListResult)
    fun get(type: MovieListType): MoviesListResult?
}