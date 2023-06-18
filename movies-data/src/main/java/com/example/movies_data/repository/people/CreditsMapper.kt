package com.example.movies_data.repository.people

import com.example.movie_domain.people.CastEntity
import com.example.movie_domain.people.CreditsEntity
import com.example.movie_domain.people.CrewEntity
import com.example.movies_data.apipojo.Cast
import com.example.movies_data.apipojo.Crew
import com.example.movies_data.apipojo.CreditsResults

interface CreditsMapper {
    fun map(credits: CreditsResults): CreditsEntity
}