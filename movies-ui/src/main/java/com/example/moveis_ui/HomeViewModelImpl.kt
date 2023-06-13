package com.example.moveis_ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie_domain.GetNowShowingMoviesUseCase
import com.example.movie_domain.GetPopularMoviesUseCase
import com.example.movie_domain.GetTopRatedMoviesUseCase
import com.example.movie_domain.GetUpcomingMoviesUseCase
import com.example.movie_domain.MovieEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.cancellation.CancellationException
import javax.inject.Inject

@HiltViewModel
class HomeViewModelImpl @Inject constructor(
    private val getNowShowingMoviesUseCase: GetNowShowingMoviesUseCase,
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase
): ViewModel() {//, HomeViewModel {
    private val _nowShowingFlow = MutableStateFlow<HomeState>(HomeState.Loading)
    val nowShowingFlow: StateFlow<HomeState>
        get() = _nowShowingFlow

    private val _upcomingFlow = MutableStateFlow<HomeState>(HomeState.Loading)
    val upcomingFlow: StateFlow<HomeState>
        get() = _upcomingFlow

    private val _topRatedFlow = MutableStateFlow<HomeState>(HomeState.Loading)
    val topRatedFlow: StateFlow<HomeState>
        get() = _topRatedFlow

    private val _popularFlow = MutableStateFlow<HomeState>(HomeState.Loading)
    val popularFlow: StateFlow<HomeState>
        get() = _popularFlow

    init {
        Log.v("HomeViewModel", "init")
//        viewModelScope.launch(CoroutineExceptionHandler {
//                coroutineContext, throwable ->
//            _nowShowingFlow.value = HomeState.Error
//        }) {
//            withContext(Dispatchers.Default) {
//                val movies = getNowShowingMoviesUseCase.invoke()
//                _nowShowingFlow.value = HomeState.Ready(movies)
//            }
//        }

        launch(_nowShowingFlow) { getNowShowingMoviesUseCase.invoke() }
        launch(_upcomingFlow) { getUpcomingMoviesUseCase.invoke() }
        launch(_topRatedFlow) { getTopRatedMoviesUseCase.invoke() }
        launch(_popularFlow) { getPopularMoviesUseCase.invoke() }

    }

    private fun launch(flow: MutableStateFlow<HomeState>, get: suspend () -> List<MovieEntity>) {
        CoroutineScope(Dispatchers.Default).launch(CoroutineExceptionHandler {
                coroutineContext, throwable ->
            flow.value = HomeState.Error
        }) {
            val movies = get()
            flow.value = HomeState.Ready(movies)
        }.invokeOnCompletion {
            if (it !=null && it.cause !is CancellationException) {
                flow.value = HomeState.Error
            }
        }
    }

    private fun getNowShowing() {

    }

    private fun getUpcoming() {

    }

    private fun getTopRated() {

    }

    private fun getPopular() {

    }
}