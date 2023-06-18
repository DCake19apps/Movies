package com.example.movies_data.repository.detail

import com.example.movie_domain.details.MoviesDetailsEntity
import com.example.movies_data.apipojo.MovieDetailsResult

interface MoviesDetailsMapper {
    fun map(movieDetailsResult: MovieDetailsResult): MoviesDetailsEntity
}