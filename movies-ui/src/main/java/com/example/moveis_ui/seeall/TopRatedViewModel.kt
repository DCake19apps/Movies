package com.example.moveis_ui.seeall

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moveis_ui.MovieListState
import com.example.movie_domain.list.GetTopRatedMoviesUseCase
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
class TopRatedViewModel @Inject constructor(
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase
): ViewModel() {

    private val _seeAllTopRatedFlow = MutableStateFlow<MovieListState>(MovieListState.Loading)
    val seeAllTopRatedFlow: StateFlow<MovieListState>
        get() = _seeAllTopRatedFlow

    init {
        initialize()
    }

    fun initialize() {
        load(1)
    }

    fun load(page: Int) {
        viewModelScope.launch(CoroutineExceptionHandler {
                coroutineContext, throwable ->
            _seeAllTopRatedFlow.value = MovieListState.Error
        }) {
            withContext(Dispatchers.Default) {
                val movies = getTopRatedMoviesUseCase.invoke(page)

                _seeAllTopRatedFlow.value = MovieListState.Ready(movies.list, movies.complete, movies.lastPage)
            }
        }.invokeOnCompletion {
            if (it !=null && it.cause !is CancellationException) {
                _seeAllTopRatedFlow.value = MovieListState.Error
            }
        }
    }
}