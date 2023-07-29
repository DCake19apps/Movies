package com.example.moveis_ui.seeall

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moveis_ui.MovieListState
import com.example.movie_domain.list.GetUpcomingMoviesUseCase
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
class UpcomingViewModel @Inject constructor(
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase
): ViewModel() {

    private val _seeAllUpcomingFlow = MutableStateFlow<MovieListState>(MovieListState.Loading)
    val seeAllUpcomingFlow: StateFlow<MovieListState>
        get() = _seeAllUpcomingFlow

    init {
        initialize()
    }

    fun initialize() {
        load(1)
    }

    override fun onCleared() {
        super.onCleared()
        Log.v("UpcomingViewModel", "onCleared")
    }

    fun load(page: Int) {
        Log.v("UpcomingViewModel", "init")
        viewModelScope.launch(CoroutineExceptionHandler {
                coroutineContext, throwable ->
            Log.v("UpcomingViewModel", "${throwable.message}")
            _seeAllUpcomingFlow.value = MovieListState.Error
        }) {
            withContext(Dispatchers.Default) {
                val movies = getUpcomingMoviesUseCase.invoke(page)

                _seeAllUpcomingFlow.value = MovieListState.Ready(movies.list, movies.complete, movies.lastPage)
            }
        }.invokeOnCompletion {
            if (it !=null && it.cause !is CancellationException) {
                _seeAllUpcomingFlow.value = MovieListState.Error
            }
            Log.v("UpcomingViewModel", "completion: ${it?.message}")
        }
    }
}