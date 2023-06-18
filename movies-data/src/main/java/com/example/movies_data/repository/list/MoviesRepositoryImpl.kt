package com.example.movies_data.repository.list

import com.example.movie_domain.list.DiscoverFilter
import com.example.movie_domain.list.MovieEntity
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
    private val nowPlayingDataRetrieverManager: DataRetrieverManager<List<MovieEntity>>,
    private val upcomingDataRetrieverManager: DataRetrieverManager<List<MovieEntity>>,
    private val topRatedDataRetrieverManager: DataRetrieverManager<List<MovieEntity>>,
    private val popularDataRetrieverManager: DataRetrieverManager<List<MovieEntity>>,
    private val discoverDataRetrieverManager: DataRetrieverManager<List<MovieEntity>>,
    private val searchDataRetrieverManager: DataRetrieverManager<List<MovieEntity>>
    ): MoviesRepository {

    override suspend fun getNowShowing(): List<MovieEntity> {
        return nowPlayingDataRetrieverManager.get{ retrieve(MovieListType.NowPlaying) }
    }

    override suspend fun getUpcoming(): List<MovieEntity> {
        return upcomingDataRetrieverManager.get{ retrieve(MovieListType.Upcoming) }
    }

    override suspend fun getTopRated(): List<MovieEntity> {
        return topRatedDataRetrieverManager.get{ retrieve(MovieListType.TopRated) }
    }

    override suspend fun getPopular(): List<MovieEntity> {
        return popularDataRetrieverManager.get{ retrieve(MovieListType.Popular) }
    }

    override suspend fun getDiscoverResults(filter: DiscoverFilter): List<MovieEntity> {
        return discoverDataRetrieverManager.get{ retrieve(MovieListType.DiscoverResults(filter)) }
    }

    override suspend fun getSearchResults(term: String): List<MovieEntity> {
        return searchDataRetrieverManager.get{ retrieve(MovieListType.SearchResults(term)) }
    }

    private suspend fun retrieve(type: MovieListType): List<MovieEntity> {
        val cachedMovies = cache.get(type)
        return if (cachedMovies == null) {
            val apiMovies = when(type) {
                MovieListType.NowPlaying -> api.getNowPlaying(apiKeyProvider.getApiKey(), 1)
                MovieListType.Upcoming -> api.getUpcoming(apiKeyProvider.getApiKey(), 1)
                MovieListType.Popular -> api.getPopular(apiKeyProvider.getApiKey(), 1)
                MovieListType.TopRated -> api.getTopRated(apiKeyProvider.getApiKey(), 1)
                is MovieListType.DiscoverResults -> api.getDiscoverResults(
                    apiKeyProvider.getApiKey(),
                    if (type.filter.sortBy == SortBy.POPULARITY) "popularity.desc" else "vote_average.desc",
                    type.filter.minVoteAverage.toString(),
                    if (type.filter.minVoteAverage > 1) "10" else "0",
                    type.filter.genres.toString().drop(1).dropLast(1).replace(" ", ""),
                    type.filter.releaseYear?.toString()?:"",
                    1
                )
                is MovieListType.SearchResults ->
                    api.getSearchResults(
                        apiKeyProvider.getApiKey(),
                        type.term,
                        1
                    )
            }
            cache.save(type, apiMovies)
            mapper.map(apiMovies)
        } else {
            mapper.map(cachedMovies)
        }
    }

//    @OptIn(ExperimentalCoroutinesApi::class)
//    override suspend fun getUpcoming(): List<MovieEntity> {
//        if (channel.isClosedForReceive) {
//            channel = Channel()
//        }
//
//        if (job?.isActive != true) {
//            job = CoroutineScope(Dispatchers.IO).launch {
//                val cachedMovies = cache.get()
//                val movies = if (cachedMovies == null) {
//                    val apiMovies = api.getUpcoming(apiKeyProvider.getApiKey(), 1)
//                    cache.save(apiMovies)
//                    mapper.map(apiMovies)
//                } else {
//                    mapper.map(cachedMovies)
//                }
//                channel.let {
//                    if (!it.isClosedForSend) {
//                        it.send(movies)
//                        it.close()
//                    }
//                }
//            }
//        }
//        return coroutineScope {
//            coroutineContext.job.invokeOnCompletion {
//                channel.close()
//            }
//            channel.receive()
//        }
//    }

    /*
    override suspend fun getUpcoming(): List<MovieEntity> {
        return coroutineScope {
            println("Upcoming: MoviesRepository:getUpcoming")
            val cachedMovies = cache.get()

            if (cachedMovies == null) {
                val apiMovies = api.getUpcoming(apiKeyProvider.getApiKey(), 1)
                delay(5000)
                cache.save(apiMovies)
                mapper.map(apiMovies)
            } else {
                mapper.map(cachedMovies)
            }
        }
    }
*/
    /*
    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getUpcoming(): List<MovieEntity> {
        CoroutineScope(Dispatchers.IO).launch {
            println("Upcoming: MoviesRepository:getUpcoming")
            channel = produce {
                val cachedMovies = cache.get()
                if (cachedMovies == null) {
                    val apiMovies = api.getUpcoming(apiKeyProvider.getApiKey(), 1)
                    //delay(5000)
                    cache.save(apiMovies)
                    mapper.map(apiMovies)
                } else {
                    mapper.map(cachedMovies)
                }
            }
        }
        return coroutineScope {
            channel.receive()
        }
    }
*/
//    @OptIn(ExperimentalCoroutinesApi::class)
//    override suspend fun getUpcoming(): List<MovieEntity> {
//        println("Upcoming: closed for receive: ${channel.isClosedForReceive}")
//
//        if (channel.isClosedForReceive) {
//            channel = Channel()
//        }
//
//        val page = 1
//        if (job?.isActive != true) {
//            job = CoroutineScope(Dispatchers.IO).launch {
//                println("Upcoming: MoviesRepository:getUpcoming")
//                val cachedMovies = cache.get()
//                val movies = if (cachedMovies == null) {
//                    val apiMovies = api.getUpcoming(apiKeyProvider.getApiKey(), page)
//                    delay(10000)
//                    cache.save(apiMovies)
//                    mapper.map(apiMovies)
//                } else {
//                    mapper.map(cachedMovies)
//                }
//                println("Upcoming: closed for send: ${channel.isClosedForSend}")
//                val c = channel
//                if (!c.isClosedForSend) {
//                    c.send(movies)
//                    println("Upcoming: sent")
//                    c.close()
//                }
//            }
//            job?.invokeOnCompletion { println("Upcoming: repo job completed") }
//        }
//        return coroutineScope {
//            coroutineContext.job.invokeOnCompletion {
//                println("Upcoming: job completed")
//                channel.close()
//            }
//            channel.receive()
//        }
//    }
}