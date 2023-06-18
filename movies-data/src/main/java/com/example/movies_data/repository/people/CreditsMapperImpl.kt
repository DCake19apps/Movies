package com.example.movies_data.repository.people

import com.example.movie_domain.people.CastEntity
import com.example.movie_domain.people.CreditsEntity
import com.example.movie_domain.people.CrewEntity
import com.example.movies_data.apipojo.Cast
import com.example.movies_data.apipojo.CreditsResults
import com.example.movies_data.apipojo.Crew

class CreditsMapperImpl: CreditsMapper {

    override fun map(credits: CreditsResults): CreditsEntity {
        return CreditsEntity(
            credits.cast?.mapNotNull { map(it) }?: emptyList(),
            credits.crew?.mapNotNull { map(it) }?: emptyList()
        )
    }

    fun map(cast: Cast?): CastEntity? {
        return cast?.id?.let {
            CastEntity(
                it,
                cast.name?:"",
                cast.character?:"",
                "https://image.tmdb.org/t/p/original${cast.profilePath}"
            )
        }
    }

    fun map(crew: Crew?): CrewEntity? {
        return crew?.id?.let {
            CrewEntity(
                it,
                crew.name?:"",
                crew.job?:"",
                "https://image.tmdb.org/t/p/original${crew.profilePath}"
            )
        }
    }
}