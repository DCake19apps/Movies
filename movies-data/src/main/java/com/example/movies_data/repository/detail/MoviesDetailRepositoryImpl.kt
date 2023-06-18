package com.example.movies_data.repository.detail

import com.example.movie_domain.details.MoviesDetailRepository
import com.example.movie_domain.details.MoviesDetailsEntity
import com.example.movies_data.DataRetrieverManager
import com.example.movies_data.api.MovieDetailsApi
import com.example.movies_data.apikey.ApiKeyProvider
import com.example.movies_data.cache.MoviesDetailsCache

class MoviesDetailRepositoryImpl(
    private val apiKeyProvider: ApiKeyProvider,
    private val api: MovieDetailsApi,
    private val cache: MoviesDetailsCache,
    private val mapper: MoviesDetailsMapper,
    private val detailsDataRetrieverManager: DataRetrieverManager<MoviesDetailsEntity>
): MoviesDetailRepository {

    override suspend fun getMovieDetails(id: Int): MoviesDetailsEntity {
        return detailsDataRetrieverManager.get { retrieve(id) }
    }

    private suspend fun retrieve(id: Int): MoviesDetailsEntity {
        val cached = cache.get(id)

        val moviesDetailsResults = if (cached != null) {
           cached
        } else {
            val apiMoviesDetail = api.getMovieInfo(id.toString(), apiKeyProvider.getApiKey())
            cache.save(id, apiMoviesDetail)
            apiMoviesDetail
        }

        return mapper.map(moviesDetailsResults)
    }

}