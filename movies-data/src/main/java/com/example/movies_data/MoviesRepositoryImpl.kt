package com.example.movies_data

import com.example.movie_domain.MovieEntity
import com.example.movie_domain.MoviesRepository
import com.example.movies_data.cache.MoviesCache

class MoviesRepositoryImpl(
    private val apiKeyProvider: ApiKeyProvider,
    private val api: MoviesApi,
    private val cache: MoviesCache,
    private val mapper: MoviesMapper,
    private val nowPlayingDataRetriever: DataRetriever<List<MovieEntity>>,
    private val upcomingDataRetriever: DataRetriever<List<MovieEntity>>,
    private val topRatedDataRetriever: DataRetriever<List<MovieEntity>>,
    private val popularDataRetriever: DataRetriever<List<MovieEntity>>
    ): MoviesRepository {

    override suspend fun getNowShowing(): List<MovieEntity> {
        return nowPlayingDataRetriever.get{ retrieve(MovieListType.NOW_PLAYING) }
    }

    override suspend fun getUpcoming(): List<MovieEntity> {
        return upcomingDataRetriever.get{ retrieve(MovieListType.UPCOMING) }
    }

    override suspend fun getTopRated(): List<MovieEntity> {
        return topRatedDataRetriever.get{ retrieve(MovieListType.TOP_RATED) }
    }

    override suspend fun getPopular(): List<MovieEntity> {
        return popularDataRetriever.get{ retrieve(MovieListType.POPULAR) }
    }

    private suspend fun retrieve(type: MovieListType): List<MovieEntity> {
        val cachedMovies = cache.get(type)
        return if (cachedMovies == null) {
            val apiMovies = when(type) {
                MovieListType.NOW_PLAYING -> api.getNowPlaying(apiKeyProvider.getApiKey(), 1)
                MovieListType.UPCOMING -> api.getUpcoming(apiKeyProvider.getApiKey(), 1)
                MovieListType.POPULAR -> api.getPopular(apiKeyProvider.getApiKey(), 1)
                MovieListType.TOP_RATED -> api.getTopRated(apiKeyProvider.getApiKey(), 1)
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