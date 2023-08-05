package com.example.moveis_ui

import androidx.lifecycle.SavedStateHandle
import com.example.moveis_ui.details.CastState
import com.example.moveis_ui.details.CrewState
import com.example.moveis_ui.details.DetailsState
import com.example.moveis_ui.details.DetailsViewModel
import com.example.movie_domain.details.GetMoviesDetailsUseCase
import com.example.movie_domain.details.MoviesDetailsEntity
import com.example.movie_domain.people.CastEntity
import com.example.movie_domain.people.CreditsEntity
import com.example.movie_domain.people.CrewEntity
import com.example.movie_domain.people.GetCreditsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

@OptIn(ExperimentalCoroutinesApi::class)
class DetailsViewModelTest {

    private val moviesDetailsEntity = MoviesDetailsEntity(
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

    private val cast = (1..3).map {
        CastEntity(
            it,
            "Name $it",
            "Character $it",
            "profile_path_$it"
        )
    }
    private val crew = (1..3).map {
        CrewEntity(
            it,
            "Name $it",
            "Job $it",
            "profile_path_$it"
        )
    }

    private val creditsEntity = CreditsEntity(cast, crew)

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Before
    fun init() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun test_success() = runTest {
        val id = 1

        val ssh = SavedStateHandle()
        ssh["movieId"] = id.toString()

        val detailsUseCase = mock<GetMoviesDetailsUseCase> {
            onBlocking { invoke(id) } doReturn moviesDetailsEntity
        }

        val creditsUseCase = mock<GetCreditsUseCase> {
            onBlocking { invoke(id) } doReturn creditsEntity
        }

        val viewModel = DetailsViewModel(ssh, detailsUseCase, creditsUseCase)

        assert(viewModel.detailsFlow.value == DetailsState.Ready(moviesDetailsEntity))
        assert(viewModel.castFlow.value == CastState.Ready(cast))
        assert(viewModel.crewFlow.value == CrewState.Ready(crew))
    }

    @Test
    fun test_error() = runTest {
        val id = 1

        val ssh = SavedStateHandle()
        ssh["movieId"] = id.toString()

        val creditsUseCase = mock<GetCreditsUseCase> {
            onBlocking { invoke(id) } doReturn creditsEntity
        }

       val detailsUseCase = mock<GetMoviesDetailsUseCase> {
           onBlocking { invoke(id) }.thenThrow() //doThrow(Exception())
        }

        val viewModel = DetailsViewModel(ssh, detailsUseCase, creditsUseCase)

        assert(viewModel.detailsFlow.value == DetailsState.Error)
    }

}