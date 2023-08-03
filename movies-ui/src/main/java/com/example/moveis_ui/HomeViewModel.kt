package com.example.moveis_ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie_domain.list.GetNowPlayingMoviesUseCase
import com.example.movie_domain.list.GetPopularMoviesUseCase
import com.example.movie_domain.list.GetTopRatedMoviesUseCase
import com.example.movie_domain.list.GetUpcomingMoviesUseCase
import com.example.movie_domain.list.MovieEntity
import com.example.movie_domain.list.Movies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.cancellation.CancellationException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase
): ViewModel() {

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
        launch(_nowShowingFlow) { getNowPlayingMoviesUseCase.invoke() }
        launch(_upcomingFlow) { getUpcomingMoviesUseCase.invoke() }
        launch(_topRatedFlow) { getTopRatedMoviesUseCase.invoke() }
        launch(_popularFlow) { getPopularMoviesUseCase.invoke() }
    }

    private fun launch(flow: MutableStateFlow<HomeState>, get: suspend () -> Movies) {
        viewModelScope.launch(CoroutineExceptionHandler {
                coroutineContext, throwable ->
            flow.value = HomeState.Error
        }) {
            withContext(Dispatchers.Default) {
                val movies = get()
                flow.value = HomeState.Ready(movies.list)
            }
        }.invokeOnCompletion {
            if (it !=null && it.cause !is CancellationException) {
                flow.value = HomeState.Error
            }
        }
    }

}