package com.example.movies_data.repository.list

import com.example.movie_domain.list.DiscoverFilter
import com.example.movie_domain.list.MovieEntity
import com.example.movie_domain.list.Movies
import com.example.movie_domain.list.MoviesRepository
import com.example.movie_domain.list.SortBy
import com.example.movies_data.apikey.ApiKeyProvider
import com.example.movies_data.DataRetrieverManager
import com.example.movies_data.api.MoviesApi
import com.example.movies_data.cache.MoviesCache

class MoviesRepositoryImpl(
    private val apiKeyProvider: ApiKeyProvider,
    private val api: MoviesApi,
    private val cache: MoviesCache,
    private val mapper: MoviesMapper,
    private val nowPlayingDataRetrieverManager: DataRetrieverManager<Movies>,
    private val upcomingDataRetrieverManager: DataRetrieverManager<Movies>,
    private val topRatedDataRetrieverManager: DataRetrieverManager<Movies>,
    private val popularDataRetrieverManager: DataRetrieverManager<Movies>,
    private val discoverDataRetrieverManager: DataRetrieverManager<Movies>,
    private val searchDataRetrieverManager: DataRetrieverManager<Movies>
    ): MoviesRepository {

    override suspend fun getNowShowing(page: Int): Movies {
        return nowPlayingDataRetrieverManager.get{ retrieve(MovieListType.NowPlaying, page) }
    }

    override suspend fun getUpcoming(page: Int): Movies {
        return upcomingDataRetrieverManager.get{ retrieve(MovieListType.Upcoming, page) }
    }

    override suspend fun getTopRated(page: Int): Movies {
        return topRatedDataRetrieverManager.get{ retrieve(MovieListType.TopRated, page) }
    }

    override suspend fun getPopular(page: Int): Movies {
        return popularDataRetrieverManager.get{ retrieve(MovieListType.Popular, page) }
    }

    override suspend fun getDiscoverResults(filter: DiscoverFilter, page: Int): Movies {
        return discoverDataRetrieverManager.get{ retrieve(MovieListType.DiscoverResults(filter), page) }
    }

    override suspend fun getSearchResults(term: String, page: Int): Movies {
        return searchDataRetrieverManager.get{ retrieve(MovieListType.SearchResults(term), page) }
    }

    private suspend fun retrieve(type: MovieListType, page: Int): Movies {
        val cachedMovies = cache.get(type)

        return if (cachedMovies.isEmpty() || page > ((cachedMovies.last().page) ?: 0)) {
            val apiMovies = when(type) {
                MovieListType.NowPlaying -> api.getNowPlaying(apiKeyProvider.getApiKey(), page)
                MovieListType.Upcoming -> api.getUpcoming(apiKeyProvider.getApiKey(), page)
                MovieListType.Popular -> api.getPopular(apiKeyProvider.getApiKey(), page)
                MovieListType.TopRated -> api.getTopRated(apiKeyProvider.getApiKey(), page)
                is MovieListType.DiscoverResults -> api.getDiscoverResults(
                    apiKeyProvider.getApiKey(),
                    if (type.filter.sortBy == SortBy.POPULARITY) "popularity.desc" else "vote_average.desc",
                    type.filter.minVoteAverage.toString(),
                    if (type.filter.minVoteAverage > 1) "10" else "0",
                    type.filter.genres.toString().drop(1).dropLast(1).replace(" ", ""),
                    type.filter.releaseYear?.toString()?:"",
                    page
                )
                is MovieListType.SearchResults ->
                    api.getSearchResults(
                        apiKeyProvider.getApiKey(),
                        type.term,
                        page
                    )
            }
            cache.save(type, apiMovies)
            mapper.map(cache.get(type))
        } else {
            mapper.map(cachedMovies)
        }
    }

}