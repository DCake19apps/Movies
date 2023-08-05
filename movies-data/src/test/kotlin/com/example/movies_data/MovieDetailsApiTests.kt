package com.example.movies_data

import com.example.movies_data.api.MovieDetailsApi
import com.example.movies_data.apikey.ApiKeyProvider
import com.example.movies_data.cache.MoviesDetailsCacheImpl
import com.example.movies_data.repository.detail.MoviesDetailRepositoryImpl
import com.example.movies_data.repository.detail.MoviesDetailsMapperImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.net.HttpURLConnection

@OptIn(ExperimentalCoroutinesApi::class)
class MovieDetailsApiTests {

    private val key = "key"

    private var mockWebServer = MockWebServer()
    private lateinit var movieDetailsApi: MovieDetailsApi
    private lateinit var moviesDetailRepository: MoviesDetailRepositoryImpl

    @Before
    fun init() {
        mockWebServer = MockWebServer()
        mockWebServer.start(8080)

        val retrofitClient = RetrofitClient(
            object : OkHttpClientProvider { override fun getClient(): OkHttpClient? = null }
        )
        retrofitClient.setHttpUrl(mockWebServer.url("/"))
        movieDetailsApi = retrofitClient.create(MovieDetailsApi::class.java)
        moviesDetailRepository = MoviesDetailRepositoryImpl(
            object : ApiKeyProvider {
                override fun getApiKey(): String {
                    return key
                }
            },
            movieDetailsApi,
            MoviesDetailsCacheImpl(),
            MoviesDetailsMapperImpl()
        )
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `test api call`() = runTest {
        val id = 1

        val expected = MovieDetailsApiTestData.getMovieDetailEntity(id)

        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(TestUtils.getJsonPath("api/details/details_1.json"))

        val dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return if (request.method == "GET" && request.path == "/3/movie/$id?api_key=$key") {
                    response
                } else {
                    MockResponse().setResponseCode(404)
                }
            }
        }

        mockWebServer.dispatcher = dispatcher

        val actual = moviesDetailRepository.getMovieDetails(id)
        assert(actual == expected)
    }

}