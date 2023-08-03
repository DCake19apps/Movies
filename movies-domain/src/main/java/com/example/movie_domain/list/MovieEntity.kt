package com.example.movie_domain.list

data class MovieEntity(
    val id: Int,
    val title: String,
    val posterPath: String,
    val backdropPath: String,
    val releaseDate: String,
    val voteAverage: String
    )

data class Movies(val list: List<MovieEntity>, val complete: Boolean, val lastPage: Int)