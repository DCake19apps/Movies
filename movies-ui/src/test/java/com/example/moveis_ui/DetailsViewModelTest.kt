package com.example.moveis_ui

import androidx.lifecycle.SavedStateHandle
import com.example.moveis_ui.details.DetailsState
import com.example.moveis_ui.details.DetailsViewModel
import com.example.movie_domain.details.GetMoviesDetailsUseCase
import com.example.movie_domain.details.MoviesDetailsEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.doThrow
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

        val useCase = mock<GetMoviesDetailsUseCase> {
            onBlocking { invoke(id) } doReturn moviesDetailsEntity
        }

        val viewModel = DetailsViewModel(ssh, useCase, Dispatchers.Unconfined)

         assert(viewModel.detailsFlow.value == DetailsState.Ready(moviesDetailsEntity))
    }

    @Test
    fun test_error() = runTest {
        val id = 1

        val ssh = SavedStateHandle()
        ssh["movieId"] = id.toString()

       val useCase = mock<GetMoviesDetailsUseCase>{
            onBlocking { invoke(id) }.thenThrow() //doThrow(Exception())
        }

//        Mockito
//            .`when`(useCase.invoke(id))
//            .thenThrow()

        val viewModel = DetailsViewModel(ssh, useCase, Dispatchers.Unconfined)

        assert(viewModel.detailsFlow.value == DetailsState.Error)


    }

}