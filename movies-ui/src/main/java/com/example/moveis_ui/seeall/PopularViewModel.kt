package com.example.moveis_ui.seeall

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moveis_ui.MovieListState
import com.example.movie_domain.list.GetPopularMoviesUseCase
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
class PopularViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase
): ViewModel() {

    private val _seeAllPopularFlow = MutableStateFlow<MovieListState>(MovieListState.Loading)
    val seeAllPopularFlow: StateFlow<MovieListState>
        get() = _seeAllPopularFlow

    init {
        initialize()
    }

    fun initialize() {
        viewModelScope.launch(CoroutineExceptionHandler {
                coroutineContext, throwable ->
            _seeAllPopularFlow.value = MovieListState.Error
        }) {
            withContext(Dispatchers.Default) {
                val movies = getPopularMoviesUseCase.invoke()

                _seeAllPopularFlow.value = MovieListState.Ready(movies)
            }
        }.invokeOnCompletion {
            if (it !=null && it.cause !is CancellationException) {
                _seeAllPopularFlow.value = MovieListState.Error
            }
        }
    }

}