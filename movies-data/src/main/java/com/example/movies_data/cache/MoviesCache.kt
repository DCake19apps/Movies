package com.example.movies_data.cache

import com.example.movies_data.repository.list.MovieListType
import com.example.movies_data.apipojo.MoviesListResult

interface MoviesCache {
    fun save(type: MovieListType, moviesResult: MoviesListResult)
    fun get(type: MovieListType): List<MoviesListResult>
}