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

    private val _searchFlow = MutableStateFlow<MovieListState>(MovieListState.Ready(emptyList()))
    val searchFlow: StateFlow<MovieListState>
        get() = _searchFlow

    fun search(term: String) {
        _searchFlow.value = MovieListState.Loading
        viewModelScope.launch(CoroutineExceptionHandler {
                coroutineContext, throwable ->
            _searchFlow.value = MovieListState.Error
        }) {
            withContext(Dispatchers.Default) {
                val movies = getSearchResultsUseCase.invoke(term)

                _searchFlow.value = MovieListState.Ready(movies)
            }
        }.invokeOnCompletion {
            if (it !=null && it.cause !is CancellationException) {
                _searchFlow.value = MovieListState.Error
            }
        }
    }

}