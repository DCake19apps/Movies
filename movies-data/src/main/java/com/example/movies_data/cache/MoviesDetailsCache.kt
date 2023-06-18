package com.example.movies_data.cache

import com.example.movies_data.apipojo.MovieDetailsResult

interface MoviesDetailsCache {
    fun save(id: Int, movieDetailsResult: MovieDetailsResult)
    fun get(id: Int): MovieDetailsResult?
}