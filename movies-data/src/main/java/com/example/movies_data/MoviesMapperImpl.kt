package com.example.movies_data

import com.example.movie_domain.MovieEntity
import com.example.movies_data.apipojo.MovieResult
import com.example.movies_data.apipojo.MoviesListResult

class MoviesMapperImpl: MoviesMapper {
    override fun map(moviesListResult: MoviesListResult): List<MovieEntity> {
        return moviesListResult.results?.mapNotNull { map(it) }?: emptyList()
    }

    private fun map(result: MovieResult?): MovieEntity? {
        if (result?.id == null || result.backdropPath == null || result.posterPath == null || result.title == null)
            return null

        val voteAverage = if (result.voteAverage == null || result.voteCount == null || result.voteCount < 10)
            ""
        else
            String.format("%.1f", result.voteAverage)

        return MovieEntity(
            result.id,
            result.title,
            "https://image.tmdb.org/t/p/original${result.posterPath}",
            "https://image.tmdb.org/t/p/original${result.backdropPath}",
            result.releaseDate?.take(4)?:"",
            voteAverage
        )
    }
}