package com.example.movies2

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.rememberNavController
import com.example.movie_domain.details.MoviesDetailRepository
import com.example.movie_domain.details.MoviesDetailsEntity
import com.example.movie_domain.list.DiscoverFilter
import com.example.movie_domain.list.MovieEntity
import com.example.movie_domain.list.MoviesRepository
import com.example.movie_domain.people.CastEntity
import com.example.movie_domain.people.CreditsEntity
import com.example.movie_domain.people.CreditsRepository
import com.example.movie_domain.people.CrewEntity
import com.example.movies2.di.DataModule
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Singleton
import androidx.test.core.app.launchActivity

@UninstallModules(DataModule::class)
@HiltAndroidTest
class UITest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)
//
    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun init() {
        hiltRule.inject()
    }

    class TestMoviesRepository: MoviesRepository {
        override suspend fun getNowShowing(): List<MovieEntity> {
            return listOf(MovieEntity(1,"Title", "", "", "2000", "7.5"))
        }

        override suspend fun getUpcoming(): List<MovieEntity> {
            return emptyList()
        }

        override suspend fun getTopRated(): List<MovieEntity> {
            return emptyList()
        }

        override suspend fun getPopular(): List<MovieEntity> {
            return emptyList()
        }

        override suspend fun getDiscoverResults(filter: DiscoverFilter): List<MovieEntity> {
            return emptyList()
        }

        override suspend fun getSearchResults(term: String): List<MovieEntity> {
            return emptyList()
        }

    }

    class TestMoviesDetailRepository: MoviesDetailRepository {
        override suspend fun getMovieDetails(id: Int): MoviesDetailsEntity {
            return MoviesDetailsEntity(
                "Title",
                "",
                "",
                "Released",
                "2000",
                "7.5",
                "150",
                "$50m",
                "$100m",
                "120",
                listOf("Drama"),
                listOf("English"),
                "This is the test overview."
            )
        }

    }

    class TestCreditsRepository: CreditsRepository {
        override suspend fun getPeople(id: Int): CreditsEntity {
            return CreditsEntity(
                (0..20).map {
                    CastEntity(
                        it,
                        "Name $it",
                        "Character $it",
                        ""
                    )
                },
                (0..20).map {
                    CrewEntity(
                        it,
                        "Name $it",
                        "Job $it",
                        ""
                    )
                }
            )
        }

    }

    @Module
    @InstallIn(SingletonComponent::class)
    object TestDataModule {
        @Singleton
        @Provides
        fun provideMoviesRepository(): MoviesRepository {
            return TestMoviesRepository()
        }

        @Singleton
        @Provides
        fun provideDetailsRepository(): MoviesDetailRepository {
            return TestMoviesDetailRepository()
        }

        @Singleton
        @Provides
        fun provideCreditsRepository(): CreditsRepository {
            return TestCreditsRepository()
        }
    }

    @Test
    fun test() {
        launchActivity<MainActivity>().use {
            Thread.sleep(5000)
        }
    }

}