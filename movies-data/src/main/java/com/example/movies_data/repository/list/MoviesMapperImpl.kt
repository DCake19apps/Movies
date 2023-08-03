package com.example.movies_data.repository.list

import com.example.movie_domain.list.MovieEntity
import com.example.movie_domain.list.Movies
import com.example.movies_data.apipojo.MovieResult
import com.example.movies_data.apipojo.MoviesListResult

class MoviesMapperImpl: MoviesMapper {
    override fun map(moviesListResults: List<MoviesListResult>): Movies {
        println("discover_debug Repo: map")
        println("discover_debug Repo: ${moviesListResults.toString().take(20)}")
        if (moviesListResults.isEmpty())
            return Movies(emptyList(), true, 1)
        return Movies(
            moviesListResults.map { it.results?: emptyList() }.flatten().mapNotNull { map(it) },
            moviesListResults.last().page == moviesListResults.last().totalPages,
            moviesListResults.last().page?:1
        )
        //return moviesListResult.results?.mapNotNull { map(it) }?: emptyList()
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