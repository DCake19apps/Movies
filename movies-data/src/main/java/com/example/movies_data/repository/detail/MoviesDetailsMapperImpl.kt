package com.example.movies_data.repository.detail

import com.example.movie_domain.details.MoviesDetailsEntity
import com.example.movies_data.apipojo.MovieDetailsResult

class MoviesDetailsMapperImpl: MoviesDetailsMapper {
    override fun map(result: MovieDetailsResult): MoviesDetailsEntity {
        println("details_debug Repo: map")
        val voteAverage = if (
            result.voteAverage == null ||
            result.voteCount == null ||
            result.voteCount < 10
        )
            ""
        else
            String.format("%.1f", result.voteAverage)

        return MoviesDetailsEntity(
            result.title ?: "",
            "https://image.tmdb.org/t/p/original${result.posterPath}",
            "https://image.tmdb.org/t/p/original${result.backdropPath}",
             result.status?:"",
            result.releaseDate?.take(4)?:"",
            voteAverage,
            result.voteCount?.toString()?:"",
            result.budget?.let { getMonetaryValue(it) } ?: "",
            result.revenue?.let { getMonetaryValue(it) } ?: "",
            result.runtime?.toString()?:"",
            result.genres?.map { it?.name?:"" }?: emptyList(),
            result.spokenLanguages?.map { it?.name?:"" }?: emptyList(),
            result.overview?:""
        )
    }

    private fun getMonetaryValue(dollars: Int): String {
        return if (dollars < 1000) {
            "$$dollars"
        } else if (dollars < 1000000) {
            val displayValue = (dollars / 1000)
            "$${displayValue}k"
        } else {
            val displayValue = (dollars / 1000000)
            "$${displayValue}M"
        }
    }

}