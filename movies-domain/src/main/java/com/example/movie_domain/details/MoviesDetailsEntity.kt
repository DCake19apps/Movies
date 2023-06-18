package com.example.movie_domain.details

data class MoviesDetailsEntity(
    val title: String,
    val posterPath: String,
    val backdropPath: String,
    val releaseStatus: String,
    val releaseDate: String,
    val rating: String,
    val votes: String,
    val budget: String,
    val revenue: String,
    val runtime: String,
    val genres: List<String>,
    val languages: List<String>,
    val overview: String
    )

