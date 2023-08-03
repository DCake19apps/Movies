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
import kotlinx.coroutines.flow.onCompletion
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

    private var currentFilter: DiscoverFilter? = null

    fun discover(filter: DiscoverFilter) {
        currentFilter = filter
        load(1)
    }

    fun load(page: Int) {
        viewModelScope.launch(CoroutineExceptionHandler {
                coroutineContext, throwable ->
            println("discover_debug ViewModel exception: ${throwable.message}")
            _discoverFlow.value = MovieListState.Error
        }) {
            withContext(Dispatchers.Default) {
                currentFilter?.let {
                    val movies = getDiscoverResultsUseCase.invoke(it, page)
                    println("discover_debug ViewModel movies: ${movies.toString().take(20)}")
                    _discoverFlow.value =
                        MovieListState.Ready(movies.list, movies.complete, movies.lastPage)
                }
            }
        }.invokeOnCompletion {
            println("discover_debug invokeOnCompletion: ${it?.message}")
            if (it !=null && it.cause !is CancellationException) {
                println("discover_debug invokeOnCompletion: cancellation")
                _discoverFlow.value = MovieListState.Error
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        println("discover_debug ViewModel: onCleared")
    }
}