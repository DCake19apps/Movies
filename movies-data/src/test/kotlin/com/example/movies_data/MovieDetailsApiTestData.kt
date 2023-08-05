package com.example.movies_data

import com.example.movie_domain.details.MoviesDetailsEntity

object MovieDetailsApiTestData {

    fun getMovieDetailEntity(id: Int) = MoviesDetailsEntity(
        "Title",
        "https://image.tmdb.org/t/p/original/poster_path.jpg",
        "https://image.tmdb.org/t/p/original/backdrop_path.jpg",
        "Released",
        "2000",
        "8.5",
        "10000",
        "$1M",
        "$10M",
        "175",
        listOf("Drama", "Crime"),
        listOf("English", "Italian", "Latin"),
        "Overview"
    )

}