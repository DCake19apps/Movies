package com.example.moveis_ui.discover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moveis_ui.MovieListState
import com.example.movie_domain.list.DiscoverFilter
import com.example.movie_domain.list.GetDiscoverResultsUseCase
import com.example.movie_domain.list.SortBy
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
class DiscoverViewModel @Inject constructor(
    private val getDiscoverResultsUseCase: GetDiscoverResultsUseCase
): ViewModel() {

    private val _discoverFlow = MutableStateFlow<MovieListState>(MovieListState.Loading)
    val discoverFlow: StateFlow<MovieListState>
        get() = _discoverFlow

    fun discover(filter: DiscoverFilter) {
        viewModelScope.launch(CoroutineExceptionHandler {
                coroutineContext, throwable ->
            _discoverFlow.value = MovieListState.Error
        }) {
            withContext(Dispatchers.Default) {
                val movies = getDiscoverResultsUseCase.invoke(filter)

                _discoverFlow.value = MovieListState.Ready(movies)
            }
        }.invokeOnCompletion {
            if (it !=null && it.cause !is CancellationException) {
                _discoverFlow.value = MovieListState.Error
            }
        }
    }
}