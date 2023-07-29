package com.example.movies_data.repository.list

import com.example.movie_domain.list.MovieEntity
import com.example.movie_domain.list.Movies
import com.example.movies_data.apipojo.MoviesListResult

interface MoviesMapper {
    fun map(moviesListResults: List<MoviesListResult>): Movies
}