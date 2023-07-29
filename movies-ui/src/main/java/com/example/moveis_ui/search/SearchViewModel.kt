package com.example.moveis_ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moveis_ui.MovieListState
import com.example.movie_domain.list.GetSearchResultsUseCase
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
class SearchViewModel @Inject constructor(
    private val getSearchResultsUseCase: GetSearchResultsUseCase
): ViewModel() {

    private val _searchFlow = MutableStateFlow<MovieListState>(MovieListState.Ready(emptyList(), false, 0))
    val searchFlow: StateFlow<MovieListState>
        get() = _searchFlow

    private var currentSearchTerm = ""

    fun search(term: String) {
        load(1)
    }

    fun load(page: Int) {
        _searchFlow.value = MovieListState.Loading
        viewModelScope.launch(CoroutineExceptionHandler {
                coroutineContext, throwable ->
            _searchFlow.value = MovieListState.Error
        }) {
            withContext(Dispatchers.Default) {
                val movies = getSearchResultsUseCase.invoke(currentSearchTerm, page)

                _searchFlow.value = MovieListState.Ready(movies.list, movies.complete, movies.lastPage)
            }
        }.invokeOnCompletion {
            if (it !=null && it.cause !is CancellationException) {
                _searchFlow.value = MovieListState.Error
            }
        }
    }

}