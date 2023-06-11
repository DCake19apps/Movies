package com.example.movies_data

import com.example.movie_domain.MovieEntity
import com.example.movies_data.apipojo.MoviesListResult

interface MoviesMapper {
    fun map(moviesListResult: MoviesListResult): List<MovieEntity>
}