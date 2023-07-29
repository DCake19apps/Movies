package com.example.moveis_ui.seeall

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moveis_ui.MovieListState
import com.example.movie_domain.list.GetNowPlayingMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

@HiltViewModel
class NowPlayingViewModel@Inject constructor(
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase
): ViewModel() {

    private val _seeAllNowPlayingFlow = MutableStateFlow<MovieListState>(MovieListState.Loading)
    val seeAllNowPlayingFlow: StateFlow<MovieListState>
        get() = _seeAllNowPlayingFlow

    init {
        initialize()
    }

    fun initialize() {
        load(1)
    }

    fun load(page: Int) {
        viewModelScope.launch(CoroutineExceptionHandler {
                coroutineContext, throwable ->
            _seeAllNowPlayingFlow.value = MovieListState.Error
        }) {
            withContext(Dispatchers.Default) {
                val movies = getNowPlayingMoviesUseCase.invoke(page)

                _seeAllNowPlayingFlow.value = MovieListState.Ready(movies.list, movies.complete, movies.lastPage)
            }
        }.invokeOnCompletion {
            if (it !=null && it.cause !is CancellationException) {
                _seeAllNowPlayingFlow.value = MovieListState.Error
            }
        }
    }
}
