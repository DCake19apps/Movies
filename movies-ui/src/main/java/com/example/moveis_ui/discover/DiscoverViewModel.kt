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

    private val _discoverFlow = MutableStateFlow<MovieListState>(MovieListState.Ready(emptyList(), false, 0))
    val discoverFlow: StateFlow<MovieListState>
        get() = _discoverFlow

    private var currentFilter: DiscoverFilter? = null

    fun discover(filter: DiscoverFilter) {
        currentFilter = filter
        load(1)
    }

    fun load(page: Int) {
        viewModelScope.launch(CoroutineExceptionHandler {
                coroutineContext, throwable ->
            _discoverFlow.value = MovieListState.Error
        }) {
            withContext(Dispatchers.Default) {
                currentFilter?.let {
                    val movies = getDiscoverResultsUseCase.invoke(it, 1)

                    _discoverFlow.value =
                        MovieListState.Ready(movies.list, movies.complete, movies.lastPage)
                }
            }
        }.invokeOnCompletion {
            if (it !=null && it.cause !is CancellationException) {
                _discoverFlow.value = MovieListState.Error
            }
        }
    }
}